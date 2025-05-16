package com.example.MiniProject.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SubjectId implements Serializable {
    private String subjectCode;     // 과목 코드
    private String professorName;   // 교수 이름
    private String dayOfWeek;        // 요일
    private Integer startTime;       // 시작 시간
    private Integer endTime;         // 끝 시간
}
