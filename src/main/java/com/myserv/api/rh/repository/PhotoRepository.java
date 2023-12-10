package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.PhotoCandidate;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface PhotoRepository extends MongoRepository<PhotoCandidate,String> {
}
