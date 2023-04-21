package com.stopbanner.src.model.Post;

import lombok.*;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostGetReq {
    @NotNull
    private Long id;
}
