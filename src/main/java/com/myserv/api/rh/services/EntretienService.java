package com.myserv.api.rh.services;

import com.myserv.api.rh.model.*;
import com.myserv.api.rh.repository.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Getter
@Setter
@RequiredArgsConstructor
@Service
public class EntretienService {
    @Autowired
    private EntretienRepository entretienRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private InfocandidateRepository infocandidateRepository;
    @Autowired
    private CompteRenduRepository compteRenduRepository;
    @Autowired
    private FeedBackRepository feedBackRepository;
    public Entretien saveEntretien(Entretien entretien, String candidateId) {
        if(entretien.getFeedback()==null){
            String DefaultFeedback = "64b1af43128f38495981525a";
            FeedBack feedback = feedBackRepository.findById(DefaultFeedback).orElseThrow();
            entretien.setFeedback(feedback);

        }
        Entretien savedEntretien = entretienRepository.save(entretien);

        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow();

        if (candidate != null) {

            List<Entretien> existingEntretiens = candidate.getListEntretien();
            List<Entretien> updatedEntretiens = new ArrayList<>();
            updatedEntretiens.add(savedEntretien);

            if (existingEntretiens != null) {
                updatedEntretiens.addAll(existingEntretiens);
            }
            candidate.setListEntretien(updatedEntretiens);
            candidateRepository.save(candidate);
        }

        return savedEntretien;
    }




    public Optional<Entretien> getEntretienById(String id) {
        return entretienRepository.findById(id);
    }

    public List<Entretien> findAll(String candidateId) {
        Candidate candidate=candidateRepository.findById(candidateId).orElseThrow();

        return candidate.getListEntretien();
    }
    public void deleteById(String idEntretien, String idCandidate) {
        // Find the Entretien to be deleted
        Optional<Entretien> entretienOptional = entretienRepository.findById(idEntretien);
        if (entretienOptional.isPresent()) {
            Entretien entretien = entretienOptional.get();

            // Find the Candidate to be updated
            Optional<Candidate> candidateOptional = candidateRepository.findById(idCandidate);
            if (candidateOptional.isPresent()) {
                Candidate candidate = candidateOptional.get();

                // Check if the Entretien is in the candidate's listEntretien
                if (candidate.getListEntretien().contains(entretien)) {
                    // Remove the Entretien from the listEntretien
                    candidate.getListEntretien().remove(entretien);
                    candidateRepository.save(candidate);
                    // Find the infoCandidate to be updated
                    Optional<Infocandidate> infocandidateOptional = infocandidateRepository.findByCandidateId(idCandidate);
                    if (infocandidateOptional.isPresent()) {
                        Infocandidate infocandidate = infocandidateOptional.get();
                        infocandidate.setCandidate(candidate);
                        infocandidateRepository.save(infocandidate);
                        Optional<CompteRendu> compteRenduOptional = compteRenduRepository.findByInfocandidateId(infocandidate.getId());
                        if (compteRenduOptional.isPresent()) {
                            CompteRendu compteRendu = compteRenduOptional.get();
                            compteRendu.setInfocandidate(infocandidate);
                            compteRenduRepository.save(compteRendu);
                        }
                    }
                    // Find the infoCandidate to be updated

                }

            }

            // Delete the Entretien
            entretienRepository.delete(entretien);
        }
    }
}
