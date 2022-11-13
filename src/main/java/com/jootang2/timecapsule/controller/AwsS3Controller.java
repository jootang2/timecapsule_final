package com.jootang2.timecapsule.controller;

import com.jootang2.timecapsule.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("/change/url")
    @ResponseBody
    public String uploadFile(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return awsS3Service.saveUploadFile(multipartFile);
    }

}