package com.myserv.api.rh.controller;


import com.myserv.api.rh.model.Entretien;
import com.myserv.api.rh.services.EntretienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class EntretienController {

    @Autowired
    private EntretienService entretienService;

    @PostMapping("/api/v1/entretien/save")
    public Entretien saveEntretien(@RequestBody Entretien entretien, @RequestParam String candidateId) {
        return entretienService.saveEntretien(entretien, candidateId);
    }

    @GetMapping("/api/v1/entretien/findbyid")
    public Optional<Entretien> findbyid(@RequestParam String id ){
        
        return entretienService.getEntretienById(id);

    }


    @GetMapping("/api/v1/entretien/all/{candidateId}")
    public List<Entretien> all(@PathVariable String candidateId) {
        return entretienService.findAll(candidateId);

    }
    @DeleteMapping("/api/v1/entretien/deleteEntretien/{idEntretien}/{idCandidate}")
    public void deleteEntretienWithCandidate(
            @PathVariable String idEntretien,
            @PathVariable String idCandidate
    ) {
        entretienService.deleteById(idEntretien, idCandidate);
    }




}
