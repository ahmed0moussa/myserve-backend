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
public class RepenseQRH {
    @Id
    private String id ;

    @Indexed(name = "question")
    private QuestionRH question;

    @Indexed(name = "textRepense")
    private String textRepense;



}
