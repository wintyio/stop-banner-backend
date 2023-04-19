package com.stopbanner.src.repository;

import com.stopbanner.src.domain.City;
import com.stopbanner.src.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query(value = "SELECT party_id, COUNT(*) AS count FROM member GROUP BY party_id", nativeQuery = true)
    List<Object[]> findRankParty();
}