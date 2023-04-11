package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.domain.User;
import com.stopbanner.src.model.Post.PostCreateReq;
import com.stopbanner.src.model.Post.PostCreateRes;
import com.stopbanner.src.model.User.PostLoginRes;
import com.stopbanner.src.security.SecurityUser;
import com.stopbanner.src.service.PostService;
import com.stopbanner.src.service.UserService;
import com.stopbanner.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    private final JwtService jwtService;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(JwtService jwtService, PostService postService, UserService userService) {
        this.jwtService = jwtService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public BaseResponse<PostCreateRes> create(@AuthenticationPrincipal SecurityUser securityUser,
                                              @Valid @RequestBody PostCreateReq postCreateReq) {
        try {
            return new BaseResponse<>(postService.createPost(postCreateReq, securityUser.getUser().getSub()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping ("/get")
    public BaseResponse<PostLoginRes> login() {
        try {
            PostLoginRes postLoginRes = new PostLoginRes(userService.loginSub("1234"));
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}