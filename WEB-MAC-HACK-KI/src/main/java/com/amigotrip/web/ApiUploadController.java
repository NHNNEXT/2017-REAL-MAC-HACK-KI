package com.amigotrip.web;

import com.amigotrip.domain.Avatar;
import com.amigotrip.domain.LocalsArticle;
import com.amigotrip.domain.Photo;
import com.amigotrip.domain.User;
import com.amigotrip.exception.BadRequestException;
import com.amigotrip.repository.*;
import com.amigotrip.service.FileUploadService;
import com.amigotrip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

/**
 * Created by NEXT on 2017. 12. 2..
 */
@RestController
@Slf4j
@RequestMapping("/uploads")
public class ApiUploadController {

    @Value("${photoUpload.path}")
    private String photoUploadPath;

    @Value("${avatarUpload.path}")
    private String avatarUploadPath;

    @Resource
    AvatarRepository avatarRepository;

    @Resource
    FileUploadService fileUploadService;

    @Resource
    PhotoRepository photoRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    UserService userService;

    @Resource
    LocalsArticleRepository localsArticleRepository;

    @PostMapping("/avatars/{userId}")
    public ResponseEntity<Avatar> uploadUserAvatar(@PathVariable Long userId, MultipartFile file, Principal principal) throws IOException {
        String path = avatarUploadPath + userId;

        userService.identification(userId, principal);
        Avatar avatar = new Avatar();
        avatar.setUserId(userId);
        Avatar dbAvatar = avatarRepository.save(avatar);

        fileUploadService.fileUpload(file, userId, avatarUploadPath); // userId와 같은 이름으로 Avatar (프로필 사진) 저장

        return new ResponseEntity<Avatar>(
                dbAvatar,
                HttpStatus.OK
        );
    }

    @PostMapping("/photos/{articleId}")
    public ResponseEntity<Photo> uploadLocalArticlePhoto(@PathVariable Long articleId, MultipartFile file, Principal principal) throws IOException {

        LocalsArticle article = localsArticleRepository.findOne(articleId);
        if (article == null) throw new BadRequestException("There's no such article.");
        userService.identification(article.getWriter().getId(), principal);
        Photo photo = new Photo();
        photo.setArticleId(articleId);
        Photo dbPhoto = photoRepository.save(photo);

        fileUploadService.fileUpload(file, dbPhoto.getPhotoId(), photoUploadPath);

        article.addPhoto(dbPhoto);
        localsArticleRepository.save(article);

        return new ResponseEntity<Photo>(
                dbPhoto,
                HttpStatus.OK
        );
    }

    @GetMapping("/avatars/{avatarId}")
    public ResponseEntity<byte[]> getAvatarImage(@PathVariable Long avatarId) throws IOException {
        byte[] image = fileUploadService.getFile(avatarId, avatarUploadPath);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/photos/{photoId}")
    public ResponseEntity<byte[]> getPhotoImage(@PathVariable Long photoId) throws IOException {
        byte[] image = fileUploadService.getFile(photoId, photoUploadPath);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
