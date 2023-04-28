package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Rank.GetRankPartyRes;
import com.stopbanner.src.model.Rank.GetRankUserRes;
import com.stopbanner.src.model.Rank.GetRankNameRes;
import com.stopbanner.src.service.RankService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "유저 랭킹", notes = "가장 많이 게시글을 작성한 유저 랭킹을 조회한다.")
    public BaseResponse<List<GetRankUserRes>> getUser() {
        try {
            return new BaseResponse<>(rankService.findRankUser());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @GetMapping("/party")
    @ApiOperation(value = "정당 랭킹", notes = "가장많이 작성된 정당 랭킹을 조회한다.")
    public BaseResponse<List<GetRankPartyRes>> getParty() {
        try {
            return new BaseResponse<>(rankService.findRankParty());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @GetMapping("/name")
    @ApiOperation(value = "의원 랭킹", notes = "가장많이 작성된 의원 랭킹을 조회한다.")
    public BaseResponse<List<GetRankNameRes>> getName() {
        try {
            return new BaseResponse<>(rankService.findRankName());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}