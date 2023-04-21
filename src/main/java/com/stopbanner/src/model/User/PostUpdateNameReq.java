package com.stopbanner.src.model.User;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateNameReq {
    @NotEmpty
    private String name;
}
