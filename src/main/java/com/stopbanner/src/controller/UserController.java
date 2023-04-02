package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Login.PostLoginReq;
import com.stopbanner.src.model.Login.PostLoginRes;
import lombok.extern.slf4j.Slf4j;
import com.stopbanner.src.service.UserService;
import com.stopbanner.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /*
    @PostMapping ("/login")
    public BaseResponse<PostGoogleRes> google(@Valid @RequestBody PostGoogleReq postGoogleReq) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> resultEntity = restTemplate.getForEntity(GOOGLE_TOKEN_BASE_URL + "?id_token=" + postGoogleReq.getIdToken(), Map.class);
            if (resultEntity.getStatusCode() != HttpStatus.OK) {
                throw new BaseException(INVALID_TOKEN);
            }
            log.error(resultEntity.getBody().get("sub").toString());
            String token = userService.loginGoogle(resultEntity.getBody().get("sub").toString());
            PostGoogleRes postGoogleRes = new PostGoogleRes(token);
            return new BaseResponse<>(postGoogleRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    */

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