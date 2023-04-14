package com.stopbanner.src.model.Post;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateReq {
    @NotNull
    private MultipartFile img;
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
    @NotNull
    private Long cityId;
    @NotNull
    private Long localId;
    @NotEmpty
    private String address;
}
