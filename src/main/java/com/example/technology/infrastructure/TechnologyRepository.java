package com.example.technology.infrastructure;

import com.example.technology.domain.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {

    boolean existsByName(String name);
}

