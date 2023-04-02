package com.stopbanner.src.repository;

import com.stopbanner.src.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findBySub(String sub);
}
