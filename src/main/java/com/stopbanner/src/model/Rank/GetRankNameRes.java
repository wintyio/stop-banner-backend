package com.stopbanner.src.model.Rank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class GetRankNameRes {
    private String name;
    private Long party;
    private Long count;

    @Builder
    public GetRankNameRes(Object[] objects) {
        this.name = (String)objects[0];
        this.party = ((BigInteger)objects[1]).longValue();
        this.count = ((BigInteger)objects[2]).longValue();
    }
}
