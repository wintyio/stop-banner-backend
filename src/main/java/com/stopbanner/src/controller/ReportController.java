package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Report.PostReportReq;
import com.stopbanner.src.model.Report.PostReportRes;
import com.stopbanner.src.model.Report.GetReportRes;
import com.stopbanner.src.security.SecurityUser;
import com.stopbanner.src.service.ReportService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @PostMapping("")
    @ApiOperation(value = "신고 작성", notes = "게시글을 신고한다.")
    public BaseResponse<PostReportRes> postReport(@AuthenticationPrincipal SecurityUser securityUser,
                                                  @Valid @RequestBody PostReportReq postReportReq) {
        try {
            return new BaseResponse<>(reportService.createReport(postReportReq, securityUser.getUser()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    @ApiOperation(value = "신고 조회", notes = "신고 목록을 조회한다.")
    public BaseResponse<List<GetReportRes>> getReport() {
        try {
            return new BaseResponse<>(reportService.findAll());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}