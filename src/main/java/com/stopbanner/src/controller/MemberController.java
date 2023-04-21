package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Member.GetRankPartyRes;
import com.stopbanner.src.service.MemberService;
import com.stopbanner.src.service.PostService;
import com.stopbanner.src.service.S3Service;
import com.stopbanner.src.service.UserService;
import com.stopbanner.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    private final JwtService jwtService;
    private final PostService postService;
    private final UserService userService;
    private final MemberService memberService;
    private final S3Service s3Service;

    public MemberController(JwtService jwtService, PostService postService, UserService userService, MemberService memberService, S3Service s3Service) {
        this.jwtService = jwtService;
        this.postService = postService;
        this.userService = userService;
        this.memberService = memberService;
        this.s3Service = s3Service;
    }
}