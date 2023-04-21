package com.stopbanner.src.service;

import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.Report;
import com.stopbanner.src.model.Report.ReportCreateReq;
import com.stopbanner.src.model.Report.ReportCreateRes;
import com.stopbanner.src.model.Report.ReportRes;
import com.stopbanner.src.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.stopbanner.config.BaseResponseStatus.DATABASE_ERROR;
import static com.stopbanner.config.BaseResponseStatus.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final PostRepository postRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    public ReportCreateRes createReport(ReportCreateReq reportCreateReq, String sub) throws BaseException {
        try {
            Report report = new Report();
            report.setReportor(userRepository.findBySub(sub));
            report.setPost(postRepository.getReferenceById(reportCreateReq.getPost_id()));
            report.setContent(reportCreateReq.getContent());
            report.setCreateDate(LocalDateTime.now());
            reportRepository.save(report);
            ReportCreateRes reportCreateRes = new ReportCreateRes(1L);
            return reportCreateRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<ReportRes> findAll() throws BaseException {
        try {
            List<Report> list = reportRepository.findAllByOrderByCreateDate();
            return list.stream().map(ReportRes::new).collect(Collectors.toList());
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
