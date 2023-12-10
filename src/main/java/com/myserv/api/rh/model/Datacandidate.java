package com.myserv.api.rh.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
@Getter
@Setter
public class Datacandidate {
    @Id
    private String id ;

    private String fullName ;

    private String email;

    private String date;

    private String feedback;

    private String commentaire;

    private String recruteur;

    private String idSpecialite;

}
