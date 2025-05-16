package com.example.MiniProject.service;

import com.example.MiniProject.entity.Subject;
import com.example.MiniProject.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // 전체 과목 조회
    public List<Subject> findAllSubjects() {
        return subjectRepository.findAll();
    }

    // 과목 추가
    public void addSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    // 과목 코드로 과목 조회
    public Subject findSubjectByCode(String subjectCode) {
        return subjectRepository.findById(subjectCode)
                .orElseThrow(() -> new IllegalArgumentException("과목을 찾을 수 없습니다: " + subjectCode));
    }

    // 과목 수정
    public void updateSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    // 과목 삭제
    public void deleteSubject(String subjectCode) {
        subjectRepository.deleteById(subjectCode);
    }
}
