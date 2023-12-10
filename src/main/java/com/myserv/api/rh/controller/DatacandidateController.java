package com.myserv.api.rh.controller;

import com.myserv.api.rh.model.Datacandidate;
import com.myserv.api.rh.services.DatacandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/data")
public class DatacandidateController {
    @Autowired
    private DatacandidateService datacandidateService;
    @PostMapping("/save")
    public String saveData() {
        return datacandidateService.saveData();
    }
    @PostMapping("/upload")
    public List<Datacandidate> place(@RequestBody List<Datacandidate> datacandidate) {
        return datacandidateService.place(datacandidate);
    }
}
