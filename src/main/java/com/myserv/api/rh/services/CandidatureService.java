package com.myserv.api.rh.services;

import com.myserv.api.rh.model.*;
import com.myserv.api.rh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatureService {

    @Autowired
    private  CandidatureRepository candidatureRepository;
    @Autowired
    private InfocandidateRepository infocandidateRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private SpecialiteRepository specialiteRepository ;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedBackRepository feedBackRepository;




    public Candidature saveCandidature(Candidature candidature) {

        return candidatureRepository.save(candidature);
    }
    public List<Candidature> findByChecked(boolean checked) {
        return candidatureRepository.findByChecked(checked);
    }
    public List<Candidature> findAll() {
        return candidatureRepository.findAll();
    }
    public void deleteById(String id){
        candidatureRepository.deleteById(id);
    }
    public Optional<Candidature> fidById(String id){
        return candidatureRepository.findById(id);
    }
    public Candidate save(String candidatureId, String email, String specialiteId){

        String DefaultFeedback = "64b1af43128f38495981525a";

        FeedBack feedback = feedBackRepository.findById(DefaultFeedback).orElseThrow();
        Candidature candidature=candidatureRepository.findById(candidatureId).orElseThrow();
        candidature.setChecked(true);
        Specialite specialite = specialiteRepository.findById(specialiteId).orElseThrow();
        User user = userRepository.findByEmail(email).orElseThrow();
        UserDTO userDTO = UserDTO.fromUser(user);
        Infocandidate infocandidate=candidature.getInfocandidate();
        Candidate candidate=infocandidate.getCandidate();
        Optional<Candidate>  searchingCandidate = candidateRepository.findByEmail(candidate.getEmail());

        if (candidateRepository.existsByEmail(candidate.getEmail())) {
            //set candidate
            Candidate existingCandidate = searchingCandidate.get();
            existingCandidate.setRecruteur(userDTO);
            existingCandidate.setSpecialite(specialite);
            existingCandidate.setFeedback(feedback);
            existingCandidate.setFile(candidate.getFile());
            Optional<Infocandidate> searchingInfocandidate = infocandidateRepository.findByCandidateEmail(existingCandidate.getEmail());

            if (infocandidateRepository.existsByCandidateEmail(existingCandidate.getEmail())) {
                Infocandidate existingInfocandidate=searchingInfocandidate.get();

                existingInfocandidate.setCandidate(existingCandidate);
                existingInfocandidate.setAddress(infocandidate.getAddress());
                existingInfocandidate.setPermit(infocandidate.getPermit());
                existingInfocandidate.setAge(infocandidate.getAge());
                existingInfocandidate.setStatus(infocandidate.getStatus());
                existingInfocandidate.setDiplome(infocandidate.getDiplome());
                existingInfocandidate.setYearsOfExperience(infocandidate.getYearsOfExperience());
                existingInfocandidate.setSalary(infocandidate.getSalary());
                existingInfocandidate.setPreavis(infocandidate.getPreavis());
                existingInfocandidate.setVisa(infocandidate.getVisa());
                existingInfocandidate.setMobilite(infocandidate.getMobilite());
                existingInfocandidate.setAutredsBoites(infocandidate.getAutredsBoites());
                existingInfocandidate.setActiveCR(false);
                candidature.setInfocandidate(existingInfocandidate);

                candidatureRepository.save(candidature);
                infocandidateRepository.save(existingInfocandidate);
                return candidateRepository.save(existingCandidate);
            }else {
                candidature.setInfocandidate(infocandidate);
                candidatureRepository.save(candidature);
                infocandidate.setCandidate(existingCandidate);
                infocandidate.setActiveCR(false);
                infocandidateRepository.save(infocandidate);
                return candidateRepository.save(existingCandidate);
            }

        }else{
            candidate.setRecruteur(userDTO);
            candidate.setSpecialite(specialite);
            candidate.setFeedback(feedback);
            infocandidate.setCandidate(candidate);
            candidature.setInfocandidate(infocandidate);
            candidatureRepository.save(candidature);
            Candidate candidateSaved=candidateRepository.save(candidate);
            infocandidate.setActiveCR(false);
            infocandidateRepository.save(infocandidate);

            return candidateSaved;
        }


    }


}
