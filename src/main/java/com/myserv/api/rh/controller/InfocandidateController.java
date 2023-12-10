package com.myserv.api.rh.controller;

import com.myserv.api.rh.model.Candidate;
import com.myserv.api.rh.model.Infocandidate;
import com.myserv.api.rh.services.InfocandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/infocandidate")
public class InfocandidateController {
    @Autowired
    private InfocandidateService infocandidateService;
    @PostMapping("/save/")
    public Infocandidate createInfocandidate(@RequestBody Infocandidate infocandidate) throws IOException {

        return infocandidateService.save(infocandidate);
    }
    @GetMapping("/findbyid/{id}")
    public Optional<Infocandidate> getInfocandidate(@PathVariable String id) {
        return  infocandidateService.getById(id);

    }
}
