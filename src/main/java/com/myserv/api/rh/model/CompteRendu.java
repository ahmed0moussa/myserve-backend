package com.myserv.api.rh.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data

@Setter
@Getter
@Document
public class CompteRendu {

    @Id
    private String id ;


    @Indexed(name = "infocandidate")
    private Infocandidate infocandidate ;

    @Indexed(name = "repenseQRHList")
    private List<RepenseQRH> repenseQRHList;

    @Indexed(name = "repenseQTECHList")
    private List<RepenseQTECH> repenseQTECHList;

    private String compteRenduFile;

}
