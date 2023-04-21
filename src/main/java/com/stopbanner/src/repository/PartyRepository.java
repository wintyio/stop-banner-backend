package com.stopbanner.src.repository;

import com.stopbanner.src.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PartyRepository extends JpaRepository<Party, Long> {
}
