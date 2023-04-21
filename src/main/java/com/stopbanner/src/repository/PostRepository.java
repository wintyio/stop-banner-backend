package com.stopbanner.src.repository;

import com.stopbanner.src.domain.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByOrderByIdDesc();
    public List<Post> findTop20ByIdLessThanAndIsActiveTrueOrderByIdDesc(Long id);
    @Query(value = "SELECT name, sub, COUNT(*) AS count FROM post INNER JOIN user on post.user_id = user.id GROUP BY user_id ORDER BY COUNT(*) DESC", nativeQuery = true)
    List<Object[]> findRankUser();
}
