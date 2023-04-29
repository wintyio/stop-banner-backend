package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Admin.GetAdminLoginTokenRes;
import com.stopbanner.src.model.Admin.PatchAdminActiveReq;
import com.stopbanner.src.model.Admin.PatchAdminActiveRes;
import com.stopbanner.src.security.SecurityUser;
import com.stopbanner.src.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping ("/active")
    @ApiOperation(value = "유저 정지", notes = "유저를 정지시키거나 부활시킨다.")
    public BaseResponse<PatchAdminActiveRes> patchActive(@Valid @RequestBody PatchAdminActiveReq patchAdminActiveReq) {
        try {
            return new BaseResponse<>(userService.updateActive(patchAdminActiveReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping ("/login")
    @ApiOperation(value = "로그인 토큰 생성", notes = "로그인 토큰을 생성한다.")
    public BaseResponse<GetAdminLoginTokenRes> getLoginToken(@AuthenticationPrincipal SecurityUser securityUser) {
        try {
            return new BaseResponse<>(new GetAdminLoginTokenRes(userService.login(securityUser.getUser().getSub())));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
