package com.myserv.api.rh.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
@Getter
@Setter
public class Infocandidate {
    @Id
    private String id ;

    private Candidate candidate;

    private String address;

    private String permit;

    private Number Age;

    private String status;

    private String diplome ;

    private Number yearsOfExperience;

    private String salary;

    private String preavis;

    private String visa;

    private String mobilite;

    private String autredsBoites;

    private String image;

    private boolean activeCR;



}
