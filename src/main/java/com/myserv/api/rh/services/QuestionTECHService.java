package com.myserv.api.rh.services;

import com.myserv.api.rh.model.QuestionTECH;
import com.myserv.api.rh.model.Specialite;
import com.myserv.api.rh.repository.QuestionTECHRepository;
import com.myserv.api.rh.repository.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionTECHService {
    @Autowired
    private QuestionTECHRepository questionTECHRepository;
    @Autowired
    private SpecialiteRepository specialiteRepository ;
    public QuestionTECH createQuestion(QuestionTECH questionTECH,String specialiteId){
        Specialite specialite = specialiteRepository.findById(specialiteId).orElseThrow();
        questionTECH.setSpecialite(specialite);
        return questionTECHRepository.save(questionTECH);
    }
    public List<QuestionTECH> findBySpecialite(String specialiteId){

        return questionTECHRepository.findBySpecialiteId(specialiteId);

    }

    public List<QuestionTECH> saveAll(List<QuestionTECH> listequestionTECH, String specialiteId) {
        Specialite specialite = specialiteRepository.findById(specialiteId).orElseThrow();

        for (QuestionTECH questionTECH : listequestionTECH) {
            questionTECH.setSpecialite(specialite);
        }

        return questionTECHRepository.saveAll(listequestionTECH);
    }
    public void deleteQuestion(String questionTECHId) {
        questionTECHRepository.deleteById(questionTECHId);
    }
}
