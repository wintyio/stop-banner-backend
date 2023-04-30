package com.stopbanner.src.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String sub;
    @Column(unique = true)
    private String name;
    private String roll;
    private LocalDateTime createDate;
    private Boolean active;
    public User() {

    }
}