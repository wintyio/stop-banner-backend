package com.stopbanner.src.repository;

import com.stopbanner.src.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReportRepository extends JpaRepository<Report, Long> {
}
