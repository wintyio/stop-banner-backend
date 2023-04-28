package com.stopbanner.src.model.Report;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReportReq {
    @NotNull
    private Long forum_id;
    @NotNull
    private String classification;
    @NotEmpty
    private String content;
}
