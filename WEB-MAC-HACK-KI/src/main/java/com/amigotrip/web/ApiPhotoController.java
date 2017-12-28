package com.amigotrip.web;

import com.amigotrip.domain.LocalsArticle;
import com.amigotrip.domain.Photo;
import com.amigotrip.domain.User;
import com.amigotrip.exception.BadRequestException;
import com.amigotrip.repository.LocalsArticleRepository;
import com.amigotrip.repository.PhotoRepository;
import com.amigotrip.repository.UserRepository;
import com.amigotrip.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by NEXT on 2017. 12. 2..
 */
@RestController
@Slf4j
@RequestMapping("/photos")
public class ApiPhotoController {

    @Resource
    FileUploadService fileUploadService;

    @Resource
    PhotoRepository photoRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    LocalsArticleRepository localsArticleRepository;

    @PostMapping("/articles/{articleId}")
    public ResponseEntity<Photo> uploadPhoto(@PathVariable Long articleId, MultipartFile uploadFile, Principal principal) throws IOException {
        String fileName = uploadFile.getOriginalFilename();
        log.debug("filename : {}", fileName);

        User loginUser = userRepository.findByEmail(principal.getName());
        if (loginUser == null) throw new BadRequestException("login before uploading an image");

        LocalsArticle dbArticle = localsArticleRepository.findOne(articleId);
        if (dbArticle == null) throw new BadRequestException("There is no such article");

        Photo photo = new Photo(fileName);
        photo.setWriter(loginUser);
        Photo dbPhoto = photoRepository.save(photo);
        dbArticle.addPhoto(dbPhoto);
        localsArticleRepository.save(dbArticle);

        fileUploadService.fileUpload(uploadFile, dbPhoto.getPhotoId());

        return new ResponseEntity<Photo>(
                dbPhoto,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileUploadService.getPhoto(id));
    }
}
