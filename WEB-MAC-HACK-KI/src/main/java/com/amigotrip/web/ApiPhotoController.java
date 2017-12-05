package com.amigotrip.web;

import com.amigotrip.domain.LocalsArticle;
import com.amigotrip.domain.Photo;
import com.amigotrip.exception.BadRequestException;
import com.amigotrip.repository.LocalsArticleRepository;
import com.amigotrip.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by NEXT on 2017. 12. 2..
 */
@RestController
@Slf4j
@RequestMapping("/images")
public class ApiPhotoController {

    @Resource
    PhotoRepository photoRepository;

    @Resource
    LocalsArticleRepository localsArticleRepository;

    @PostMapping("/{article_id}")
    public String uploadPhoto(@PathVariable Long article_id, MultipartFile uploadFile) throws IOException {
        String fileName = uploadFile.getOriginalFilename();

        LocalsArticle dbArticle = localsArticleRepository.findOne(article_id);
        if (dbArticle == null) throw new BadRequestException("There is no such article");
        Photo dbPhoto = photoRepository.save(new Photo(fileName));
        dbArticle.addPhoto(dbPhoto);
        localsArticleRepository.save(dbArticle);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://image.amigotrip.co.kr/image/" + dbPhoto.getPhotoId();
        String responseString = restTemplate.postForObject(url, uploadFile, String.class);
        return responseString;
    }

    @GetMapping("/{id}")
    public byte[] getPhoto(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        byte[] photo = restTemplate.getForObject("http://image.amigotrip.co.kr/image/" + id, byte[].class);

        return photo;
    }
}
