package com.myserv.api.rh.services;

import com.myserv.api.rh.model.PhotoCandidate;
import com.myserv.api.rh.repository.PhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@Service
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepo;

    public String addPhoto(String title, MultipartFile file) throws IOException {
        PhotoCandidate photo = new PhotoCandidate(title);
        photo.setImage(
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoRepo.insert(photo); return photo.getId();
    }

    public PhotoCandidate getPhoto(String id) {
        return photoRepo.findById(id).get();
    }
}
