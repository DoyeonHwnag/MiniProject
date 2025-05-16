package com.example.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subject {

    @EmbeddedId
    private SubjectId id; // 복합키

    private String subjectName;   // 과목 이름
    private String grade;         // 학년
    private int credit;           // 학점
    private int capacity;         // 수용 가능 인원
    private String semester;      // 학기
    private String syllabusPath;  // 강의계획서 파일 경로
}
