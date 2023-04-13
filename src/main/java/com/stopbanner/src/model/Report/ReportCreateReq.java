package com.stopbanner.src.model.Report;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportCreateReq {
    @NotNull
    private Long post_id;
    @NotNull
    private String classification;
    @NotEmpty
    private String content;
}
