package com.myserv.api.rh.services;

import com.myserv.api.rh.model.*;
import com.myserv.api.rh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompteRenduService {
    @Autowired
    private EntretienRepository entretienRepository;

    @Autowired
    private CompteRenduRepository compteRenduRepository;

    @Autowired
    private InfocandidateRepository infocandidateRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RepenseQrhRepository repenseQRHRepository;

    @Autowired
    private RepenseQtechRepository repenseQtechRepository;
   public CompteRendu createCompteRendu(CompteRendu compteRendu) {

        Infocandidate infocandidate = compteRendu.getInfocandidate();
        Candidate candidate = infocandidate.getCandidate();
       // Check if ListEntretien is not null before saving
       if (candidate.getListEntretien() != null) {
           entretienRepository.saveAll(candidate.getListEntretien());
       }

       // Check if candidate is not null before saving
       if (candidate != null) {
           candidateRepository.save(candidate);
       }

       // Check if infocandidate is not null before saving
       if (infocandidate != null) {
           infocandidate.setActiveCR(true);
           infocandidateRepository.save(infocandidate);
       }

       // Check if repenseQRH is not null before saving
       if (compteRendu.getRepenseQRHList() != null) {
           repenseQRHRepository.saveAll(compteRendu.getRepenseQRHList());
       }

       // Check if repenseQTECHList is not null before saving
       if (compteRendu.getRepenseQTECHList() != null) {
           repenseQtechRepository.saveAll(compteRendu.getRepenseQTECHList());
       }
        compteRendu.setInfocandidate(infocandidate);
        return compteRenduRepository.save(compteRendu);
    }
    public CompteRendu getByIdinfocandidate(String infocandidateId){

        return compteRenduRepository.findByInfocandidate_Id(infocandidateId);
    }

}
