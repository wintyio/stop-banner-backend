package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Admin.PutActiveReq;
import com.stopbanner.src.model.Admin.PutActiveRes;
import com.stopbanner.src.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PutMapping("/active")
    public BaseResponse<PutActiveRes> putActive(@Valid @RequestBody PutActiveReq putActiveReq) {
        try {
            return new BaseResponse<>(userService.updateActive(putActiveReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
