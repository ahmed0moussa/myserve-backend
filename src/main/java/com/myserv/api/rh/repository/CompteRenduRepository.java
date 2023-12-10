package com.myserv.api.rh.repository;


import com.myserv.api.rh.model.CompteRendu;
import com.myserv.api.rh.model.Infocandidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompteRenduRepository extends MongoRepository<CompteRendu, String> {
    List<CompteRendu> findByinfocandidate_Id(String infocandidateId);
    CompteRendu findByInfocandidate_Id(String infocandidateId);

    Optional<CompteRendu> findByInfocandidateId(String infocandidateId);
}
