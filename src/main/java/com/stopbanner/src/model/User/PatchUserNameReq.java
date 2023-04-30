package com.stopbanner.src.model.User;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchUserNameReq {
    @NotEmpty
    @Size(min=1, max=10)
    private String name;
}
