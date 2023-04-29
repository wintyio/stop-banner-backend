package com.stopbanner.src.model.Forum;

import com.stopbanner.src.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class GetForumRes {
    private Long id;
    private String sub;
    private String name;
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
    public GetForumRes(Forum forum){
        this.id = forum.getId();
        this.sub = forum.getUser().getSub();
        this.name = forum.getUser().getName();
        this.img = forum.getImg();
        this.lat = forum.getLat();
        this.lng = forum.getLng();
        // this.city = post.getCity();
        this.local = forum.getLocal();
        this.address = forum.getAddress();
        this.createDate = forum.getCreateDate();
        for (Member member : forum.getMembers()) {
            this.names.add(member.getName());
            this.parties.add(member.getParty().getId());
        }
    }
    public GetForumRes() {

    }
}
