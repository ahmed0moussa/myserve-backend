package com.myserv.api.rh.services;

import com.myserv.api.rh.model.*;
import com.myserv.api.rh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DatacandidateService {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private InfocandidateRepository infocandidateRepository;
    @Autowired
    private CompteRenduRepository compteRenduRepository;
    @Autowired
    private DatacandidateRepository datacandidateRepository;
    @Autowired
    private EntretienRepository entretienRepository;
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpecialiteRepository specialiteRepository;
    @Autowired
    private QuestionRHRepository questionRHRepository;
    @Autowired
    private QuestionTECHRepository questionTECHRepository;
    private String urlCv="http://localhost:8080/uploads/Cv/Cv_";
    private String urlCr="http://localhost:8080/uploads/compteRendu/CR_";
    public String saveData(){
        List<Datacandidate> datacandidateList=datacandidateRepository.findAll();
        String pattern = "d/M/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        for (Datacandidate datacandidate : datacandidateList) {
            Entretien entretien=new Entretien();
            Candidate candidate=new Candidate();
            Infocandidate infocandidate=new Infocandidate();
            CompteRendu compteRendu=new CompteRendu();
            //Create a new  entretien
            entretien.setCommentaire(datacandidate.getCommentaire());
            LocalDate localDate = LocalDate.parse(datacandidate.getDate(), formatter);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            entretien.setDate(date);
            entretien.setDatecreation(date);
            entretien.setFeedback(getFeedbackByName(datacandidate.getFeedback()));
            entretien.setTime("12:00");
            if(datacandidate.getFeedback()=="Validé RH"||datacandidate.getFeedback()=="Non validé RH") {
                entretien.setEntretienType("rh");
            } else {
                entretien.setEntretienType("tech");
            }
            entretien=entretienRepository.save(entretien);
            // Create a new candidate
            candidate.setFullName(datacandidate.getFullName());
            candidate.setEmail(datacandidate.getEmail());
            candidate.setListEntretien(List.of(entretien));
            Optional<User> existingUser=userRepository.findByFullName(datacandidate.getRecruteur());
            if (existingUser.isPresent()) {
                User user=existingUser.get();
                UserDTO userDTO = UserDTO.fromUser(user);
                candidate.setRecruteur(userDTO);
            }else{
                UserDTO userDTO =new UserDTO();
                userDTO.setFullName(datacandidate.getRecruteur());
                candidate.setRecruteur(userDTO);
            }
            candidate.setFeedback(getFeedbackByName(datacandidate.getFeedback()));
            // Handle the Optional<Specialite>
            Optional<Specialite> optionalSpecialite = specialiteRepository.findById(datacandidate.getIdSpecialite());
            optionalSpecialite.ifPresent(candidate::setSpecialite);
            candidate.setFile(urlCv+datacandidate.getFullName()+".pdf");
            candidate=candidateRepository.save(candidate);
            // Create a new infocandidate
            infocandidate.setCandidate(candidate);
            infocandidate.setActiveCR(true);
            infocandidate=infocandidateRepository.save(infocandidate);
            // Create a new compte rendu
            compteRendu.setInfocandidate(infocandidate);
            compteRendu.setCompteRenduFile(urlCr+datacandidate.getFullName()+".pdf");
            // Fill repenses for RepenseQRHList
            List<QuestionRH> questionRHList = questionRHRepository.findAll();
            List<RepenseQRH> repenseQRHList = new ArrayList<>();

            for (QuestionRH questionRH : questionRHList) {
                RepenseQRH repenseQRH = new RepenseQRH();
                repenseQRH.setQuestion(questionRH);
                repenseQRH.setTextRepense("");
                repenseQRHList.add(repenseQRH);
            }
            compteRendu.setRepenseQRHList(repenseQRHList);
            // Fill repenses for RepenseQTECHList
            List<QuestionTECH> questionTECHList = questionTECHRepository.findBySpecialiteId(datacandidate.getIdSpecialite());
            List<RepenseQTECH> repenseQTECHList = new ArrayList<>();

            for (QuestionTECH questionTECH : questionTECHList) {
                RepenseQTECH repenseQTECH = new RepenseQTECH();
                repenseQTECH.setQuestion(questionTECH);
                repenseQTECH.setTextRepense("");
                repenseQTECHList.add(repenseQTECH);
            }

            compteRendu.setRepenseQTECHList(repenseQTECHList);
            compteRenduRepository.save(compteRendu);






        }


        return "done";
    }
    public FeedBack getFeedbackByName(String name) {
        return feedBackRepository.findByNom(name);
    }
    public List<Datacandidate> place(List<Datacandidate> datacandidate){
        return  datacandidateRepository.saveAll(datacandidate);
    }
}
