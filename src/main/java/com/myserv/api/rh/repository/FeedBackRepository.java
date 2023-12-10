package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.FeedBack;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedBackRepository extends MongoRepository<FeedBack, String> {
    FeedBack findByNom(String nom);
}
