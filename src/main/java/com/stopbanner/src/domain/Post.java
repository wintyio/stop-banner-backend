package com.stopbanner.src.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String img;
    private Double lat;
    private Double lng;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "local_id")
    private Local local;
    private String address;
    private LocalDateTime createDate;
    private Boolean isActive;

    @OneToMany(mappedBy = "post")
    private List<Member> members;
    public Post() {

    }
}