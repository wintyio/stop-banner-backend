package com.stopbanner.src.model.User;

import com.stopbanner.src.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRes {
    private String name;
    private String roll;
    private String sub;
    private LocalDateTime createDate;

    public GetUserRes(User user) {
        this.sub = user.getSub();
        this.name = user.getName();
        this.roll = user.getRoll();
        this.createDate = user.getCreateDate();
    }
}
