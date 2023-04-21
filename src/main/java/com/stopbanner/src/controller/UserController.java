package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.User.PostLoginReq;
import com.stopbanner.src.model.User.PostLoginRes;
import com.stopbanner.src.model.User.PostUpdateNameReq;
import com.stopbanner.src.model.User.PostUpdateNameRes;
import com.stopbanner.src.security.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import com.stopbanner.src.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping ("/login")
    public BaseResponse<PostLoginRes> login(@Valid @RequestBody PostLoginReq postLoginReq) {
        try {
            return new BaseResponse<>(userService.login(postLoginReq.getAccessToken()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping ("/update/name")
    public BaseResponse<PostUpdateNameRes> updateName(@Valid @RequestBody PostUpdateNameReq postUpdateNameReq,
                                                        @AuthenticationPrincipal SecurityUser securityUser) {
        try {
            return new BaseResponse<>(userService.updateName(postUpdateNameReq, securityUser.getUser().getSub()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}