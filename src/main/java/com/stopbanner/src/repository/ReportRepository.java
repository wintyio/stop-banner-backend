package com.stopbanner.src.repository;

import com.stopbanner.src.domain.Post;
import com.stopbanner.src.domain.Report;
import com.stopbanner.src.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReportRepository extends JpaRepository<Report, Long> {
    public List<Report> findAllByOrderByCreateDate();
}
