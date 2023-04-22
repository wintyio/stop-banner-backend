package com.stopbanner.src.model.Admin;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PutAdminActiveReq {
    @NotEmpty
    private String sub;
    @NotNull
    private Boolean active;
}
