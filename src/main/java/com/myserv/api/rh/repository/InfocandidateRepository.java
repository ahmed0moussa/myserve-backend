package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.Infocandidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfocandidateRepository extends MongoRepository<Infocandidate,String> {
    Optional<Infocandidate> findByCandidateEmail(String candidateEmail);
    boolean existsByCandidateEmail(String candidateEmail);
    Optional<Infocandidate> findByCandidateId(String candidateId);

}
