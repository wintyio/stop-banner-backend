package com.stopbanner.src.model.Post;

import com.stopbanner.src.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostRes {
    private Long id;
    private String sub;
    private String img;
    private Double lat;
    private Double lng;
    private City city;
    private Local local;
    private String address;
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
    }
    public PostRes() {

    }
}
