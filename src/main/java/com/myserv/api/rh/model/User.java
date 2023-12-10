package com.myserv.api.rh.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Data
@Document()
@Setter
@Getter
public class User {

    @Id
    private String id ;

    @NotBlank
    @Size(max = 40)
    @Indexed(name = "fullName")
    private String fullName;



    @NotBlank
    @Size(max = 50)
    @Email
    @Indexed(name = "email")
    private String email;

    
    @NotBlank
    @Size(max = 120)
    @Indexed(name = "password")
    private String password;

    @DBRef
    private Set<Roles> roles = new HashSet<>();

    public User(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(jsonString, User.class);
            this.id = user.getId();
            this.fullName = user.getFullName();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.roles = user.getRoles();
        } catch (Exception e) {
            // Handle the exception (e.g., log the error, throw an exception, etc.)
            // Note: Catching a generic Exception is not recommended in production code. Be more specific if possible.
        }
    }

    public User(String id,String fullName, String email, String password, Set<Roles> roles) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User() {

    }

    public User(String fullName, String email, String password, Set<Roles> roles) {
        this.fullName = fullName;

        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(String fullName,String email, String password) {
        this.fullName = fullName;

        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
