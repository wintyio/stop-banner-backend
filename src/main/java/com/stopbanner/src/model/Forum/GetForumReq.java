package com.stopbanner.src.model.Forum;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetForumReq {
    @NotNull
    private Long id;

    @NotNull
    @Range(min=1, max=20)
    private Long cnt;
}
