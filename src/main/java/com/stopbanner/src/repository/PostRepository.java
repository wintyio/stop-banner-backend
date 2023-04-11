package com.stopbanner.src.repository;

import com.stopbanner.src.domain.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByOrderByCreateDateDesc();
}
