package com.stopbanner.src.model.Forum;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostForumReqMember {
    @NotEmpty
    @Size(min=1, max=6)
    private String name;
    @NotNull
    private Long partyId;
}