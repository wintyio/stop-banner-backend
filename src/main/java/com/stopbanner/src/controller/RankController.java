package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Rank.GetRankPartyRes;
import com.stopbanner.src.model.Rank.GetRankUserRes;
import com.stopbanner.src.model.Rank.GetRankNameRes;
import com.stopbanner.src.service.PostService;
import com.stopbanner.src.service.RankService;
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
@RequestMapping("/rank")
public class RankController {

    private final JwtService jwtService;
    private final PostService postService;
    private final UserService userService;
    private final RankService rankService;
    private final S3Service s3Service;

    public RankController(JwtService jwtService, PostService postService, UserService userService, RankService rankService, S3Service s3Service) {
        this.jwtService = jwtService;
        this.postService = postService;
        this.userService = userService;
        this.rankService = rankService;
        this.s3Service = s3Service;
    }

    @GetMapping("/get/user")
    public BaseResponse<List<GetRankUserRes>> getRankUserList() {
        try {
            return new BaseResponse<>(rankService.findRankUser());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @GetMapping("/get/party")
    public BaseResponse<List<GetRankPartyRes>> getRankPartyList() {
        try {
            return new BaseResponse<>(rankService.findRankParty());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @GetMapping("/get/name")
    public BaseResponse<List<GetRankNameRes>> getRankNameList() {
        try {
            return new BaseResponse<>(rankService.findRankName());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}