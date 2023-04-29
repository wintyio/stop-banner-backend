package com.stopbanner.src.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.stopbanner.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.stopbanner.config.BaseResponseStatus.INVALID_EXT;

@RequiredArgsConstructor
@Service
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String upload(MultipartFile multipartFile) throws IOException, BaseException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        String ext = multipartFile.getContentType();

        String allow[] = {"jpeg", "jpg", "gif", "png"};
        if (!Arrays.asList(allow).contains(ext)) {
            // throw new BaseException(INVALID_EXT);
        }

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }
}