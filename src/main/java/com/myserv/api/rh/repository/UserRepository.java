package com.myserv.api.rh.repository;

import com.myserv.api.rh.model.RoleType;
import com.myserv.api.rh.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository <User, String> {

    Optional<User> findByEmail (String email);

    @Query("{'email': {$ne: ?0}}")
    List<User> findAllUsersExceptAdmin(String email);


    Boolean existsByEmail (String email);
    Optional<User> findByFullName(String fullName);


}