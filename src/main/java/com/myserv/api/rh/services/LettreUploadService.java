package com.myserv.api.rh.services;

import com.myserv.api.rh.configfile.CompteRenduStorageProperties;
import com.myserv.api.rh.configfile.FileStorageProperties;
import com.myserv.api.rh.configfile.ImageStorageProperties;
import com.myserv.api.rh.configfile.LettreStorageProperties;
import com.myserv.api.rh.model.Candidature;
import com.myserv.api.rh.repository.CandidatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Service
public class LettreUploadService {

    @Autowired
    private CandidatureRepository candidatureRepository;

    private final Path lettreUploadLocation;

    private final Path lettreDownloadLocation = Paths.get("./uploads/Lettre");

    @Autowired
    public LettreUploadService(LettreStorageProperties lettreStorageProperties) {
        this.lettreUploadLocation = Paths.get(lettreStorageProperties.getLettreDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.lettreUploadLocation);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create the directory where you want to the uploaded the files will be kept.", e);
        }
    }
    public Resource loadResource(String filename) {
        try {
            Path path = lettreUploadLocation.resolve(filename);
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

    public ResponseEntity<Candidature> uploadlettre(String candidatureId, MultipartFile file) {
        // Renormalize the file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Candidature candidature = candidatureRepository.findById(candidatureId).orElseThrow();

        try {
            // Verify if the file's name  is containing invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! File name is containing invalid path sequence " + fileName);
            }
            // Copy file to the target path (replacing existing file with the same name)
            Path targetLocation = this.lettreUploadLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String LettreUploadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/Lettre/").path(fileName).toUriString();
            candidature.setLettreDeMotivation(LettreUploadUrl);
            candidatureRepository.save(candidature);



            return ResponseEntity.ok(candidature);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(candidature);
        }

    }

    public ResponseEntity<Resource> downloadFile(String fileName) {
        try {
            Path filePath = lettreDownloadLocation.resolve(fileName).normalize();
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
