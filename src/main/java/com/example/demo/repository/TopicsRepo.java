package com.example.demo.repository;

import com.example.demo.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicsRepo extends JpaRepository<Topics, Long> {
}
