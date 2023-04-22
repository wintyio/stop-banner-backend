package com.stopbanner.src.model.Forum;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostForumReq {
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
    private List<String> names;
    private List<Long> parties;
}
