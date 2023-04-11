package com.stopbanner.src.model.Post;

import com.stopbanner.src.domain.City;
import com.stopbanner.src.domain.Local;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateReq {
    @NotEmpty
    private Long memberId;
    private MultipartFile img;
    private Long lat;
    private Long lng;
    private Long cityId;
    private Long localId;
    private String address;
}
