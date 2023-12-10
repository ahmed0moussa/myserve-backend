package com.myserv.api.rh.controller;

import com.myserv.api.rh.model.QuestionRH;
import com.myserv.api.rh.model.QuestionTECH;
import com.myserv.api.rh.services.QuestionRhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionRHController {
    @Autowired
    private QuestionRhService questionRhService;
    @PostMapping("/api/v1/questionRH/create")
    public QuestionRH create(@RequestBody QuestionRH questionRH){

        return questionRhService.createQuestion(questionRH);
    }
    @PostMapping("/api/v1/questionRH/saveAll")
    public List<QuestionRH> create(@RequestBody List<QuestionRH> listeQuestionRH){

        return questionRhService.saveAll(listeQuestionRH);
    }

    @GetMapping("/api/v1/questionRH/findAll")
    public List<QuestionRH> getAll(){
        return questionRhService.findAll();

    }
    @DeleteMapping("/api/v1/questionRH/delete/{questionRHId}")
    public ResponseEntity<String> delete(@PathVariable String questionRHId) {
        try {
            questionRhService.deleteQuestion(questionRHId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting QuestionRH with ID " + questionRHId);
        }
    }
}
