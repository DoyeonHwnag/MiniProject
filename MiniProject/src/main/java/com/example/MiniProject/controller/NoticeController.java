package com.example.MiniProject.controller;

import com.example.MiniProject.entity.Notice;
import com.example.MiniProject.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 공지사항 목록
    @GetMapping("/notices")
    public String list(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        List<Notice> notices = noticeService.findAll();
        model.addAttribute("notices", notices);

        if (isAdmin(session)) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isAdmin", false);
        }

        return "notice/notice-list";
    }

    // 공지사항 상세 보기
    @GetMapping("/notices/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);

        // 🔥 추가
        if (isAdmin(session)) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isAdmin", false);
        }

        return "notice/notice-detail";
    }


    // 공지사항 등록 폼 (관리자만)
    @GetMapping("/notices/add")
    public String addForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }
        model.addAttribute("user", session.getAttribute("user"));
        return "notice/notice-add";
    }

    // 공지사항 등록 처리
    @PostMapping("/notices/add")
    public String addNotice(@RequestParam String title,
                            @RequestParam String content,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }

        Notice notice = new Notice(title, content);
        noticeService.save(notice);
        return "redirect:/notices";
    }

    // 공지사항 수정 폼
    @GetMapping("/notices/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }

        model.addAttribute("user", session.getAttribute("user"));
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        return "notice/notice-edit";
    }

    // 공지사항 수정 처리
    @PostMapping("/notices/edit")
    public String editNotice(@RequestParam Long id,
                             @RequestParam String title,
                             @RequestParam String content,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }

        Notice notice = noticeService.findById(id);
        if (notice != null) {
            notice.setTitle(title);
            notice.setContent(content);
            noticeService.save(notice);
        }
        return "redirect:/notices";
    }

    // 공지사항 삭제
    @PostMapping("/notices/delete/{id}")
    public String deleteNotice(@PathVariable Long id,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }

        noticeService.delete(id);
        return "redirect:/notices";
    }

    private boolean isAdmin(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        String role = (String) session.getAttribute("role");
        return "ADMIN".equals(role);
    }
}
