package com.example.demo.repository;

import com.example.demo.entity.UsersExams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExamsRepo extends JpaRepository<UsersExams, Long> {
}
