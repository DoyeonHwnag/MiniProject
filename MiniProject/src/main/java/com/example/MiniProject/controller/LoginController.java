package com.example.MiniProject.controller;

import com.example.MiniProject.entity.User;
import com.example.MiniProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.mustache
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            System.out.println("로그인한 사용자 역할: " + user.getRole());

            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin/subjects";
            } else {
                return "redirect:/index";
            }
        }
        return "redirect:/login?error";  // 로그인 실패시
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 세션 무효화
        return "redirect:/index";  // 로그아웃 후 메인 페이지로 리다이렉트
    }
}
