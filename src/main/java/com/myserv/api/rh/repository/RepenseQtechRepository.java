package com.myserv.api.rh.repository;


import com.myserv.api.rh.model.RepenseQTECH;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepenseQtechRepository extends MongoRepository<RepenseQTECH, String> {
}
