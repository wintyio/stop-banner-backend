package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.config.BaseResponseStatus;
import com.stopbanner.src.model.Post.PostCreateReq;
import com.stopbanner.src.model.Post.PostCreateRes;
import com.stopbanner.src.model.Post.PostRes;
import com.stopbanner.src.model.User.PostLoginRes;
import com.stopbanner.src.security.SecurityUser;
import com.stopbanner.src.service.PostService;
import com.stopbanner.src.service.S3Service;
import com.stopbanner.src.service.UserService;
import com.stopbanner.utils.JwtService;
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

    private final JwtService jwtService;
    private final PostService postService;
    private final UserService userService;
    private final S3Service s3Service;

    public PostController(JwtService jwtService, PostService postService, UserService userService, S3Service s3Service) {
        this.jwtService = jwtService;
        this.postService = postService;
        this.userService = userService;
        this.s3Service = s3Service;
    }


    @PostMapping("/create")
    public BaseResponse<PostCreateRes> create(@AuthenticationPrincipal SecurityUser securityUser,
                                              @Valid @RequestBody PostCreateReq postCreateReq) {
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
    public BaseResponse<List<PostRes>> login() {
        try {
            return new BaseResponse<>(postService.findAll());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}