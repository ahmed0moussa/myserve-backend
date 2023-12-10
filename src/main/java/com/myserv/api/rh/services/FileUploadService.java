package com.myserv.api.rh.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.myserv.api.rh.configfile.FileStorageProperties;
import com.myserv.api.rh.model.Candidate;
import com.myserv.api.rh.model.Candidature;
import com.myserv.api.rh.model.Entretien;
import com.myserv.api.rh.repository.CandidateRepository;
import com.myserv.api.rh.repository.CandidatureRepository;
import com.myserv.api.rh.repository.EntretienRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class FileUploadService {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidatureRepository candidatureRepository;
    private final Path fileUploadLocation;
    private final Path fileDownloadLocation = Paths.get("./uploads/Cv");

    @Autowired
    public FileUploadService(FileStorageProperties fileStorageProperties) {
        this.fileUploadLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileUploadLocation);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create the directory where you want to the uploaded the files will be kept.", e);
        }
    }
    public Resource loadResource(String filename) {
        try {
            Path path = fileUploadLocation.resolve(filename);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()){
                return resource;
            }else {
                throw new RuntimeException("Fail");
            }
        }catch (MalformedURLException e){
            throw new RuntimeException("Fail");
        }
    }

    public ResponseEntity<Candidate> uploadFile(String candidateId, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow();

        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! File name is containing invalid path sequence " + fileName);
            }
            // Copy file to the target path (replacing existing file with the same name)
            Path targetLocation = this.fileUploadLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String fileUploadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/Cv/").path(fileName).toUriString();
            candidate.setFile(fileUploadUrl);
            candidateRepository.save(candidate);

                return ResponseEntity.ok(candidate);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(candidate);
            }
    }
    public ResponseEntity<Candidature> uploadFileCandidature(String candidatureId, MultipartFile file) {
        // Renormalize the file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Candidature candidature = candidatureRepository.findById(candidatureId).orElseThrow();

        try {
            // Verify if the file's name  is containing invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! File name is containing invalid path sequence " + fileName);
            }
            // Copy file to the target path (replacing existing file with the same name)
            Path targetLocation = this.fileUploadLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String fileUploadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/Cv/").path(fileName).toUriString();
            candidature.getInfocandidate().getCandidate().setFile(fileUploadUrl);
            candidatureRepository.save(candidature);

            return ResponseEntity.ok(candidature);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(candidature);
        }
    }

    public ResponseEntity<Resource> downloadFile(String fileName) {
        try {
            Path filePath = fileDownloadLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error downloading file: " + ex.getMessage());
        }
    }
}

