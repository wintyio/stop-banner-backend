package com.stopbanner.src.repository;

import com.stopbanner.src.domain.City;
import com.stopbanner.src.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
}
