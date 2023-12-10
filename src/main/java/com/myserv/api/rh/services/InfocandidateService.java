package com.myserv.api.rh.services;

import com.myserv.api.rh.model.Infocandidate;
import com.myserv.api.rh.repository.InfocandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InfocandidateService {
    @Autowired
    private InfocandidateRepository infocandidateRepository;
    public Infocandidate save(Infocandidate infocandidate){
        return infocandidateRepository.save(infocandidate);
    }
    public Optional<Infocandidate> getById(String id){
        return infocandidateRepository.findByCandidateId(id);
    }
}
