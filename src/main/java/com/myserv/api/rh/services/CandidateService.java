package com.myserv.api.rh.services;

import com.myserv.api.rh.model.*;
import com.myserv.api.rh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private SpecialiteRepository specialiteRepository ;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InfocandidateRepository infocandidateRepository;


    public Candidate save(Candidate candidate, String email, String specialiteId){
        String DefaultFeedback = "64b1af43128f38495981525a";
        FeedBack feedback = feedBackRepository.findById(DefaultFeedback).orElseThrow();
        Specialite specialite = specialiteRepository.findById(specialiteId).orElseThrow();
        User user = userRepository.findByEmail(email).orElseThrow();
        UserDTO userDTO = UserDTO.fromUser(user);

        String candidateEmail = candidate.getEmail();
        if (candidateEmail != null && candidateRepository.findByEmail(candidateEmail).isPresent()) {

            throw new IllegalArgumentException("Email already exists");
        }else {
            candidate.setFeedback(feedback);
            candidate.setSpecialite(specialite);
            candidate.setRecruteur(userDTO);
            Infocandidate infocandidate=new Infocandidate();
            Candidate savedcandidate =candidateRepository.save(candidate);
            infocandidate.setCandidate(candidate);
            infocandidate.setActiveCR(false);
            infocandidateRepository.save(infocandidate);
            return savedcandidate;
        }

    }
    public List<Candidate> findAll() {

        return candidateRepository.findAll();
    }
    public Optional<Candidate> findById(String id){
        return candidateRepository.findById(id);
    }
    public List<Candidate> findBySpecialiteId(String SpecialiteId) {

        return candidateRepository.findBySpecialiteId(SpecialiteId);
    }
}
