package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.Candidate;
import com.myserv.api.rh.model.Entretien;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {

    Boolean existsByEmail (String email);
    Optional<Candidate> findByEmail(String email);
    @Query("{'specialite.id':  ?0}")
    List<Candidate> findBySpecialiteId(String specialiteId);
}

