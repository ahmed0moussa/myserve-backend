package com.myserv.api.rh.controller;

import com.myserv.api.rh.model.Candidate;
import com.myserv.api.rh.services.CandidateService;
import com.myserv.api.rh.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/save/{specialiteId}")
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate, Principal principal,
                                                     @PathVariable String specialiteId) throws IOException {
        String username = principal.getName();
        Candidate savedCandidate = candidateService.save(candidate, username, specialiteId);
        return new ResponseEntity<>(savedCandidate, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidateService.findAll();
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable String id) {
        Optional<Candidate> candidate = candidateService.findById(id);
        if (candidate.isPresent()) {
            return new ResponseEntity<>(candidate.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/specialite/{specialiteId}")
    public List<Candidate> getBySpecialiteId(@PathVariable String specialiteId) {
        return candidateService.findBySpecialiteId(specialiteId);
    }
    @PostMapping ("/uploadFile/{candidateId}")
    public ResponseEntity<Candidate> uploadFile(@PathVariable String candidateId, @RequestParam("file") MultipartFile file) {

        return fileUploadService.uploadFile(candidateId,file);
    }

    }
