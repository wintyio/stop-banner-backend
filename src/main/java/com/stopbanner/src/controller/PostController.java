package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.config.BaseResponseStatus;
import com.stopbanner.src.model.Post.PostCreateReq;
import com.stopbanner.src.model.Post.PostCreateRes;
import com.stopbanner.src.model.Post.PostGetReq;
import com.stopbanner.src.model.Post.PostRes;
import com.stopbanner.src.security.SecurityUser;
import com.stopbanner.src.service.PostService;
import com.stopbanner.src.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final S3Service s3Service;

    public PostController(PostService postService, S3Service s3Service) {
        this.postService = postService;
        this.s3Service = s3Service;
    }

    @PostMapping("/create")
    public BaseResponse<PostCreateRes> create(@AuthenticationPrincipal SecurityUser securityUser,
                                              @Valid PostCreateReq postCreateReq) {
        try {
            String url = s3Service.upload(postCreateReq.getImg());
            return new BaseResponse<>(postService.createPost(postCreateReq, url, securityUser.getUser().getSub()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        } catch (IOException exception) {
            return new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public BaseResponse<List<PostRes>> getPostResList(@Valid @RequestBody PostGetReq PostGetReq) {
        try {
            return new BaseResponse<>(postService.findAll(PostGetReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}