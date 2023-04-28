package com.stopbanner.src.repository;

import com.stopbanner.src.domain.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ForumRepository extends JpaRepository<Forum, Long> {
    public List<Forum> findTop20ByIdLessThanAndIsActiveTrueOrderByIdDesc(Long id);
    @Query(value = "SELECT name, sub, COUNT(*) AS count FROM forum INNER JOIN user on forum.user_id = user.id GROUP BY user_id ORDER BY COUNT(*) DESC", nativeQuery = true)
    List<Object[]> findRankUser();
}
