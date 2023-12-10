package com.myserv.api.rh.services;

import com.myserv.api.rh.model.QuestionRH;
import com.myserv.api.rh.model.QuestionTECH;
import com.myserv.api.rh.model.Specialite;
import com.myserv.api.rh.repository.QuestionRHRepository;
import com.myserv.api.rh.repository.QuestionTECHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionRhService {
    @Autowired
    private QuestionRHRepository questionRHRepository;

    public QuestionRH createQuestion(QuestionRH questionRH){

        return questionRHRepository.save(questionRH);
    }
    public List<QuestionRH> findAll(){

        return questionRHRepository.findAll();

    }

    public List<QuestionRH> saveAll(List<QuestionRH> listeQuestionRH) {
        return questionRHRepository.saveAll(listeQuestionRH);
    }
    public void deleteQuestion(String questionRHId) {
        questionRHRepository.deleteById(questionRHId);
    }
}
