package com.myserv.api.rh.auth;

import lombok.RequiredArgsConstructor;

import java.util.List;


public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;

    private String fullName;

    private String email;
    private List<String> roles;

    public JwtResponse(String token, String id, String email, String fullName, List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.fullName=fullName;
        this.roles = roles;


    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
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





    public List<String> getRoles() {
        return roles;
    }
}