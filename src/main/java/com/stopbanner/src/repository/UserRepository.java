package com.stopbanner.src.repository;

import com.stopbanner.src.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findBySub(String sub);
}
