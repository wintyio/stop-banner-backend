package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.config.BaseResponseStatus;
import com.stopbanner.src.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("images") MultipartFile multipartFile) {
        try {
            // multipartFile.getOriginalFilename()
            String url = s3Service.upload(multipartFile);
            return url;
        }
        // catch (BaseException exception) {
        //     return new BaseResponse<>((exception.getStatus()));
        // }
        catch (IOException exception) {
            return "err";
            // return new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
