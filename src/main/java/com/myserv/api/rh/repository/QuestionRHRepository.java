package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.Candidate;
import com.myserv.api.rh.model.QuestionRH;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRHRepository extends MongoRepository<QuestionRH, String> {
}
