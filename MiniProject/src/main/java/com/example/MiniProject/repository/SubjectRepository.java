package com.example.MiniProject.repository;

import com.example.MiniProject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, String> {
    // 기본 CRUD 제공

}
