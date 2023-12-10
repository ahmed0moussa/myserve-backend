package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.Candidate;
import com.myserv.api.rh.model.Candidature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatureRepository extends MongoRepository<Candidature, String> {
    List<Candidature> findByChecked(boolean checked);
}
