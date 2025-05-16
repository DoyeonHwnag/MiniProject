package com.example.MiniProject.service;

import com.example.MiniProject.entity.User;
import com.example.MiniProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SignUpService {

    private final UserRepository userRepository;


    @Autowired
    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입 처리 (비밀번호를 평문으로 저장, 관리자 코드 추가)
    public User registerUser(String username, String password, String adminCode) {
        String role = "USER";

        // adminCode가 "admin123"일 경우 관리자 계정으로 생성
        if ("admin123".equals(adminCode)) {
            role = "ADMIN";
        }

        User user = new User(username, password, role, LocalDateTime.now());
        return userRepository.save(user);
    }

    // 로그인 처리 (사용자 조회 및 평문 비밀번호 비교)
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
