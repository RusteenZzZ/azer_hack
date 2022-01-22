package com.example.demo.repository;

import com.example.demo.entity.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswersRepo extends JpaRepository<Answers, Long> {
}
