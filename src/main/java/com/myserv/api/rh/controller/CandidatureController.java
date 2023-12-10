package com.myserv.api.rh.controller;
import com.myserv.api.rh.model.Candidate;
import com.myserv.api.rh.model.Candidature;

import com.myserv.api.rh.model.Entretien;
import com.myserv.api.rh.services.CandidatureService;
import com.myserv.api.rh.services.FileUploadService;
import com.myserv.api.rh.services.LettreUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {

    @Autowired
    private CandidatureService candidatureService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private LettreUploadService lettreUploadService;
    @Autowired
    public CandidatureController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    @PostMapping("/save")
    public Candidature saveCandidature(@RequestBody Candidature candidature) {
        return candidatureService.saveCandidature(candidature);
    }

    @GetMapping("/checked/true")
    public List<Candidature> findCheckedCandidatures() {
        return candidatureService.findByChecked(true);
    }

    @GetMapping("/checked/false")
    public List<Candidature> findUncheckedCandidatures() {
        return candidatureService.findByChecked(false);
    }
    @GetMapping("/findAll")
    public List<Candidature> findall() {
        return candidatureService.findAll();
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        this.candidatureService.deleteById(id);
    }
    @PostMapping ("/save/uploadFile/{candidatureId}")
    public ResponseEntity<Candidature> uploadFileCandidature(@PathVariable String candidatureId,
                                                             @RequestParam("file") MultipartFile file

    ) {

        return fileUploadService.uploadFileCandidature(candidatureId,file);
    }
    @PostMapping ("/save/uploadLettre/{candidatureId}")
    public ResponseEntity<Candidature> uploadLettreCandidature(@PathVariable String candidatureId,
                                                             @RequestParam("lettreDeMotivation") MultipartFile lettreDeMotivation
    ) {
        return lettreUploadService.uploadlettre(candidatureId,lettreDeMotivation);

    }
    @GetMapping("/findbyid/{id}")
    public Optional<Candidature> findById(@PathVariable String id) {
        return candidatureService.fidById(id);
    }
    @GetMapping("/downloadFile/{imageName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String imageName) {
        return fileUploadService.downloadFile(imageName);
    }
    @PostMapping( "/savecandidate/{candidatureId}/{specialiteId}" )
    public Candidate place(@PathVariable String candidatureId,
                           Principal principal,
                           @PathVariable String specialiteId) throws IOException {
        String username = principal.getName();
        return candidatureService.save(candidatureId, username, specialiteId);
    }
}
