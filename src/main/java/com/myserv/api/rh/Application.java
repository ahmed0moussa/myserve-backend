package com.myserv.api.rh;

import com.myserv.api.rh.model.RoleType;
import com.myserv.api.rh.model.Roles;
import com.myserv.api.rh.repository.RoleRepository;
import com.myserv.api.rh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class Application {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserService userservise;

	public static void main(String[] args) {


		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userservise){
		return args -> {

			this.userservise.createadmine();


		};
	}


}
