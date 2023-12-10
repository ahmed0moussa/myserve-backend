package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.RoleType;
import com.myserv.api.rh.model.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository <Roles, String> {

    Optional<Roles> findByName(RoleType name);
    Boolean existsByName(RoleType name);
}
