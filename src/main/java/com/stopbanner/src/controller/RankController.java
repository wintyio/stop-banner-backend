package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Rank.GetRankPartyRes;
import com.stopbanner.src.model.Rank.GetRankUserRes;
import com.stopbanner.src.model.Rank.GetRankNameRes;
import com.stopbanner.src.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rank")
public class RankController {
    private final RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping("/user")
    public BaseResponse<List<GetRankUserRes>> getUser() {
        try {
            return new BaseResponse<>(rankService.findRankUser());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @GetMapping("/party")
    public BaseResponse<List<GetRankPartyRes>> getParty() {
        try {
            return new BaseResponse<>(rankService.findRankParty());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @GetMapping("/name")
    public BaseResponse<List<GetRankNameRes>> getName() {
        try {
            return new BaseResponse<>(rankService.findRankName());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}