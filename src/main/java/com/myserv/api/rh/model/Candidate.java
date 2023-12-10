package com.myserv.api.rh.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document()
@Getter
@Setter
public class Candidate {
    @Id
    private String id ;

    private String fullName ;

    private String email;

    private String phone;

    private UserDTO recruteur ;

    private String file ;

    private Specialite specialite ;

    private FeedBack feedback;

    private List<Entretien> listEntretien;



}
