package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.config.BaseResponseStatus;
import com.stopbanner.src.model.User.*;
import com.stopbanner.src.security.SecurityUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.stopbanner.src.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.stopbanner.config.BaseResponseStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping ("")
    @ApiOperation(value = "유저 정보", notes = "유저 정보를 조회한다.")
    public BaseResponse<GetUserRes> get(@AuthenticationPrincipal SecurityUser securityUser) {
        try {
            return new BaseResponse<>(new GetUserRes(securityUser.getUser()));
        } catch (Exception exception) {
            return new BaseResponse<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping ("/login")
    @ApiOperation(value = "유저 로그인", notes = "유저 로그인 한다.")
    public BaseResponse<PostUserLoginRes> postLogin(@Valid @RequestBody PostUserLoginReq postUserLoginReq) {
        try {
            return new BaseResponse<>(userService.kakaoLogin(postUserLoginReq.getAccessToken()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping ("/name")
    @ApiOperation(value = "유저 이름 변경", notes = "유저 이름을 변경한다.")
    public BaseResponse<PatchUserNameRes> patchName(@Valid @RequestBody PatchUserNameReq patchUserNameReq,
                                                  @AuthenticationPrincipal SecurityUser securityUser) {
        try {
            return new BaseResponse<>(userService.updateName(patchUserNameReq, securityUser.getUser().getSub()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}