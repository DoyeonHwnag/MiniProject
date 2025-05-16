package com.example.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor  // 기본 생성자
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private LocalDateTime createdAt;

    //(String title, String content) 생성자 추가
    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now(); // 생성 시점 자동 저장
    }
}
