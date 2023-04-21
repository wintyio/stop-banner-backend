package com.stopbanner.src.model.Rank;

import lombok.*;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class GetRankPartyRes {
    private Long party_id;
    private Long count;

    @Builder
    public GetRankPartyRes(Object[] objects) {
        this.party_id = ((BigInteger)objects[0]).longValue();
        this.count = ((BigInteger)objects[1]).longValue();
    }
}
