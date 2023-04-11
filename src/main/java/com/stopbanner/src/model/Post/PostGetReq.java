package com.stopbanner.src.model.Post;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostGetReq {
    @NotEmpty
    private Long memberId;
    private String img;
    private Long lat;
    private Long lng;
    private Long cityId;
    private Long localId;
    private String address;
}
