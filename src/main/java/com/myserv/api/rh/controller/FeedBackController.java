package com.myserv.api.rh.controller;


import com.myserv.api.rh.model.FeedBack;
import com.myserv.api.rh.repository.FeedBackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedBackController  {

    @Autowired
    private FeedBackRepository feedBackRepository;


    @PostMapping("/api/v1/feedback/create")
    public String place(@RequestBody FeedBack feedBack){
        feedBackRepository.save(feedBack);
        return "cava";
    }

    @GetMapping("/api/v1/feedback/all")
    public List<FeedBack> all(){
      return feedBackRepository.findAll();

    }


}
