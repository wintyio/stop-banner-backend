package com.stopbanner.src.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private String img;
    public Party() {

    }
}