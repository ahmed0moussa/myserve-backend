package com.myserv.api.rh.controller;

import com.myserv.api.rh.model.*;
import com.myserv.api.rh.services.CompteRenduService;
import com.myserv.api.rh.services.CompteRenduUploadService;
import com.myserv.api.rh.services.FileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class CompteRenduController {

    @Autowired
    private CompteRenduService compteRenduService;

    @Autowired
    private FileImageService fileImageService;

    @Autowired
    private CompteRenduUploadService compteRenduUploadService;

   @PostMapping(value = "/api/v1/compterendu/create" )
    public CompteRendu create(@RequestBody CompteRendu compteRendu){
        return compteRenduService.createCompteRendu(compteRendu);

    }
    @GetMapping("/api/v1/compterendu/findbyid/{id}")
    public CompteRendu findbyIdCandidate(@PathVariable String id ){

        return compteRenduService.getByIdinfocandidate(id);
    }


    @PostMapping ("/api/v1/compterendu/uploadImage/{infocandidateId}")
    public ResponseEntity<CompteRendu> uploadImage(@PathVariable String infocandidateId, @RequestParam("image") MultipartFile file) {

        return fileImageService.uploadImage(infocandidateId,file);
    }
    @PostMapping ("/api/v1/compterendu/uploadCompteRendu/{compteRenduId}")
    public ResponseEntity<CompteRendu> uploadCompteRendu(@PathVariable String compteRenduId, @RequestParam("filecompteRendu") MultipartFile file) {

        return compteRenduUploadService.uploadCompteRendu(compteRenduId,file);
    }



}
