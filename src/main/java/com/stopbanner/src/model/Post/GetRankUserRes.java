package com.stopbanner.src.model.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class GetRankUserRes {
    private String name;
    private String sub;
    private Long count;

    @Builder
    public GetRankUserRes(Object[] objects) {
        this.name = (String)objects[0];
        this.sub = (String)objects[1];
        this.count = ((BigInteger)objects[2]).longValue();
    }
}
