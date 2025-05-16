package com.example.MiniProject.controller;

import com.example.MiniProject.entity.User;
import com.example.MiniProject.service.SignUpService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignUpController {

    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signUpPage() {
        return "signup"; // signup.mustache
    }
    // 회원가입 처리
    @PostMapping("/signup")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "adminCode", required = false) String adminCode,
            Model model) {

        User user = signUpService.registerUser(username, password, adminCode);
        model.addAttribute("message", "회원가입이 완료되었습니다.");
        return "redirect:/login";
    }
}

