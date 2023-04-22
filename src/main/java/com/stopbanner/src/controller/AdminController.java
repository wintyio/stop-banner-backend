package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Admin.PatchAdminActiveReq;
import com.stopbanner.src.model.Admin.PatchAdminActiveRes;
import com.stopbanner.src.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
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
    public BaseResponse<PatchAdminActiveRes> patchActive(@Valid @RequestBody PatchAdminActiveReq patchAdminActiveReq) {
        try {
            return new BaseResponse<>(userService.updateActive(patchAdminActiveReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
