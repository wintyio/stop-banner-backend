package com.stopbanner.src.model.Report;

import com.stopbanner.src.domain.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportRes {
    private Long id;
    private String reportorSub;
    private Long post_id;
    private String content;
    @Builder
    public ReportRes(Report report) {
        this.id = report.getId();
        this.reportorSub = report.getReportor().getSub();
        this.post_id = report.getPost().getId();
        this.content = report.getContent();
    }
    public ReportRes() {

    }
}
