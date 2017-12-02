package com.amigotrip.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by NEXT on 2017. 12. 2..
 */
@RestController
@Slf4j
@RequestMapping("/images")
public class ApiImageController {

    @PostMapping("")
    public String uploadImage(MultipartFile uploadFile) throws IOException {
        String fileName = uploadFile.getOriginalFilename();

        return fileName;
    }
}
