package com.stopbanner.src.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    public City() {

    }
}