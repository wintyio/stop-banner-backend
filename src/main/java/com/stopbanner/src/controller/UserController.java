package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.User.*;
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
    public BaseResponse<PostUserLoginRes> postLogin(@Valid @RequestBody PostUserLoginReq postUserLoginReq) {
        try {
            return new BaseResponse<>(userService.login(postUserLoginReq.getAccessToken()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping ("/name")
    public BaseResponse<PatchUserNameRes> patchName(@Valid @RequestBody PatchUserNameReq patchUserNameReq,
                                                  @AuthenticationPrincipal SecurityUser securityUser) {
        try {
            return new BaseResponse<>(userService.updateName(patchUserNameReq, securityUser.getUser().getSub()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}