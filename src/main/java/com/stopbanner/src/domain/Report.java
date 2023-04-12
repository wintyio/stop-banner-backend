package com.stopbanner.src.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "reportor_id")
    private User reportor;
    @ManyToOne
    @JoinColumn(name = "reported_id")
    private User reported;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private String content;
    private LocalDateTime createDate;

    public Report() {

    }
}