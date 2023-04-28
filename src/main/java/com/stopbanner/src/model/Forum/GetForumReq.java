package com.stopbanner.src.model.Forum;

import lombok.*;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetForumReq {
    @NotNull
    private Long id;
}
