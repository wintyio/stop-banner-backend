package com.stopbanner.src.service;

import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.Report;
import com.stopbanner.src.domain.User;
import com.stopbanner.src.model.Report.PostReportReq;
import com.stopbanner.src.model.Report.PostReportRes;
import com.stopbanner.src.model.Report.GetReportRes;
import com.stopbanner.src.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.stopbanner.config.BaseResponseStatus.DATABASE_ERROR;
import static com.stopbanner.config.BaseResponseStatus.FAIL_ANONYMOUS;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final ForumRepository forumRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    public PostReportRes createReport(PostReportReq postReportReq, User user) throws BaseException {
        if (user.getName().equals("익명")) {
            throw new BaseException(FAIL_ANONYMOUS);
        }
        try {
            Report report = new Report();
            report.setReportor(user);
            report.setForum(forumRepository.getReferenceById(postReportReq.getForumId()));
            report.setContent(postReportReq.getContent());
            report.setCreateDate(LocalDateTime.now());
            reportRepository.save(report);
            PostReportRes postReportRes = new PostReportRes(1L);
            return postReportRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetReportRes> findAll() throws BaseException {
        try {
            List<Report> list = reportRepository.findAllByOrderByCreateDate();
            return list.stream().map(GetReportRes::new).collect(Collectors.toList());
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
