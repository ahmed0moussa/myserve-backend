package com.myserv.api.rh.model;

import nonapi.io.github.classgraph.json.Id;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PhotoCandidate")

public class PhotoCandidate {
    @Id
    private String id;

    private String title;

    private Binary image;
    public PhotoCandidate(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setImage(Binary image) {
        this.image=image;
    }
}
