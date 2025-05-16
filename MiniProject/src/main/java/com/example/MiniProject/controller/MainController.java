package com.example.MiniProject.controller;

import com.example.MiniProject.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        // 세션에서 사용자 정보 가져오기
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);  // 사용자 정보를 모델에 추가
        return "index";
    }
}
