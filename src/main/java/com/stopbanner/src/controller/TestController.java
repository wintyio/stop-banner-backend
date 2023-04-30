package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Admin.GetAdminLoginTokenRes;
import com.stopbanner.src.model.Admin.PatchAdminActiveReq;
import com.stopbanner.src.model.Admin.PatchAdminActiveRes;
import com.stopbanner.src.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    private final UserService userService;
    public TestController(UserService userService) {
        this.userService = userService;
    }

    /*
    @GetMapping ("/login")
    public BaseResponse<GetAdminLoginTokenRes> getLoginToken() {
        try {
            return new BaseResponse<>(new GetAdminLoginTokenRes(userService.login("1234")));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    */
}
