package com.myserv.api.rh.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class QuestionTECH {
    @Id
    private String id ;

    @Indexed(name = "textQuestion")
    private String textQuestion;

    @Indexed(name = "specialite")
    private Specialite specialite;

}
