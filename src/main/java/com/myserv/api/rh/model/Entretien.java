package com.myserv.api.rh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document
@Setter
@Getter

public class Entretien {

    @Id
    private String id;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Indexed(name = "datecreation")
    private Date datecreation;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Indexed(name = "date")
    private Date date;

    @Indexed(name = "time")
    @JsonFormat(pattern = "HH:mm")
    private String time;

    @Indexed(name = "feedback")
    private FeedBack feedback;

    @Indexed(name = "entretienType")
    private String entretienType;

    @Indexed(name = "commentaire")
    private String commentaire;



}
