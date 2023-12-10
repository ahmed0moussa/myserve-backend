package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.Specialite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpecialiteRepository extends MongoRepository<Specialite, String> {
}
