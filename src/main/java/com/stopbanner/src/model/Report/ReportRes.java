package com.stopbanner.src.model.Report;

import com.stopbanner.src.domain.City;
import com.stopbanner.src.domain.Local;
import com.stopbanner.src.domain.Post;
import com.stopbanner.src.domain.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReportRes {
    private Long id;
    private String reportorSub;
    private String reportedSub;
    private Long post_id;
    private String content;
    @Builder
    public ReportRes(Report report) {
        this.id = report.getId();
        this.reportorSub = report.getReportor().getSub();
        this.reportedSub = report.getReported().getSub();
        this.post_id = report.getPost().getId();
        this.content = report.getContent();
    }
    public ReportRes() {

    }
}
