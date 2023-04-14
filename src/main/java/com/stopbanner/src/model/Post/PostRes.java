package com.stopbanner.src.model.Post;

import com.stopbanner.src.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class PostRes {
    private Long id;
    private String sub;
    private String img;
    private Double lat;
    private Double lng;
    private City city;
    private Local local;
    private String address;
    private List<String> names = new ArrayList<>();
    private List<Long> parties = new ArrayList<>();
    private LocalDateTime createDate;
    @Builder
    public PostRes(Post post){
        this.id = post.getId();
        this.sub = post.getUser().getSub();
        this.img = post.getImg();
        this.lat = post.getLat();
        this.lng = post.getLng();
        this.city = post.getCity();
        this.local = post.getLocal();
        this.address = post.getAddress();
        this.createDate = post.getCreateDate();
        for (Member member : post.getMembers()) {
            this.names.add(member.getName());
            this.parties.add(member.getParty().getId());
        }
    }
    public PostRes() {

    }
}
