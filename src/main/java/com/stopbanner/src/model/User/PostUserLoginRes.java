package com.stopbanner.src.model.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserLoginRes {
    private String token;
    private String name;
}
