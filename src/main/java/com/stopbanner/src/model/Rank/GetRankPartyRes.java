package com.stopbanner.src.model.Rank;

import com.stopbanner.src.domain.Report;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

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
