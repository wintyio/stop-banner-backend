package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.src.model.Post.PostRes;
import com.stopbanner.src.model.Report.ReportCreateReq;
import com.stopbanner.src.model.Report.ReportCreateRes;
import com.stopbanner.src.model.Report.ReportRes;
import com.stopbanner.src.security.SecurityUser;
import com.stopbanner.src.service.ReportService;
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


    @PostMapping("/create")
    public BaseResponse<ReportCreateRes> create(@AuthenticationPrincipal SecurityUser securityUser,
                                              @Valid @RequestBody ReportCreateReq reportCreateReq) {
        try {
            return new BaseResponse<>(reportService.createReport(reportCreateReq, securityUser.getUser().getSub()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // @Secured("ROLE_ADMIN")
    @GetMapping("/get")
    public BaseResponse<List<ReportRes>> login() {
        try {
            return new BaseResponse<>(reportService.findAll());
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}