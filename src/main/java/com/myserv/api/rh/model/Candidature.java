package com.myserv.api.rh.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
@Getter
@Setter
public class Candidature {
    @Id
    private String id ;
    private String disponible;
    private Boolean checked = false;
    private Infocandidate infocandidate;
    private String domaine;
    private String TypeDeMission;
    private String technologies;
    private String certifications;
    private String contrat;
    private String lettreDeMotivation;

}
