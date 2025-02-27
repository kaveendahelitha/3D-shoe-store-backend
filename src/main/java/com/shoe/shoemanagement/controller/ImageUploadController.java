package com.shoe.shoemanagement.controller;

import com.shoe.shoemanagement.entity.ImageModel;
import com.shoe.shoemanagement.repository.ImageRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin
public class ImageUploadController {

    private static String imageDirectory = System.getProperty("user.dir") + "/images/";
    private final ImageRepository imageRepository;

    public ImageUploadController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @RequestMapping(value = "/image", produces = {MediaType.IMAGE_PNG_VALUE, "application/json"})
    public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file,
                                         @RequestParam("imageName") String name) {
        makeDirectoryIfNotExist(imageDirectory);
        Path fileNamePath = Paths.get(imageDirectory,
                name.concat(".").concat(FilenameUtils.getExtension(file.getOriginalFilename())));

        try {
            Files.write(fileNamePath, file.getBytes());

            // Create an ImageModel instance and save it to the database
            ImageModel imageModel = new ImageModel();
            imageModel.setImageName(name);
            imageModel.setImageDescription("Image uploaded successfully");
            imageRepository.save(imageModel); // Save to database

            return new ResponseEntity<>(name, HttpStatus.CREATED);
        } catch (IOException ex) {
            return new ResponseEntity<>("Image is not uploaded", HttpStatus.BAD_REQUEST);
        }
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
