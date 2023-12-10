package com.myserv.api.rh.controller;


import com.myserv.api.rh.model.Specialite;
import com.myserv.api.rh.repository.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
public class SpecialiteController  {
    @Autowired
    private SpecialiteRepository specialiteRepository;


    @PostMapping("/api/v1/specialite/create")
    public Specialite place(@RequestBody Specialite specialite){
        return specialiteRepository.save(specialite);

    }

    @GetMapping("/api/v1/specialite/all")
    public List<Specialite> all(){
        return specialiteRepository.findAll();

    }
}
