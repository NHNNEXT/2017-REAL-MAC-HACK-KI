package com.amigotrip.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by NEXT on 2017. 12. 7..
 */
@Slf4j
@Service("service.fileUploadService")
@ComponentScan({"com.amigotrip.domain"})
public class FileUploadService {

    public void fileUpload(MultipartFile uploadFile, long fileId, String path) throws IOException {

        if (!uploadFile.isEmpty()) {
            File file = new File(path + fileId);
            uploadFile.transferTo(file);
        }
    }

    public byte[] getFile(long fileId, String path) throws IOException {
        Path p = Paths.get(path + fileId);
        return Files.readAllBytes(p);
    }

    public void deleteFile(String filename, String path) throws IOException {
        Files.deleteIfExists(Paths.get(path, filename));
    }
}
