package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.Entretien;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntretienRepository extends MongoRepository <Entretien, String> {




}