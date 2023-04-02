package com.stopbanner.src.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;
    private String name;
    private String img;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "local_id")
    private Local local;
    private Long zone; // 0 : 없음, 1 : 갑, 2 : 을
    public Member() {

    }
}