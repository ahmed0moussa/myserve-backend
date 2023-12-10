package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.Datacandidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatacandidateRepository extends MongoRepository<Datacandidate, String> {
}
