package com.stopbanner.src.model.Report;

import com.stopbanner.src.domain.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GetReportRes {
    private Long id;
    private String reportorSub;
    private Long forumId;
    private String content;
    private LocalDateTime createDate;
    @Builder
    public GetReportRes(Report report) {
        this.id = report.getId();
        this.reportorSub = report.getReportor().getSub();
        this.forumId = report.getForum().getId();
        this.content = report.getContent();
        this.createDate = report.getCreateDate();
    }
    public GetReportRes() {

    }
}
