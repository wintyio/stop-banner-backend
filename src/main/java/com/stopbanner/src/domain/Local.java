package com.stopbanner.src.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    public Local() {

    }
}