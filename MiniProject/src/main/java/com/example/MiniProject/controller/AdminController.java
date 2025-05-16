package com.example.MiniProject.controller;

import com.example.MiniProject.entity.Subject;
import com.example.MiniProject.service.SubjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {

    private final SubjectService subjectService;

    @Autowired
    public AdminController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // 과목 리스트 조회
    @GetMapping("/admin/subjects")
    public String listSubjects(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }

        model.addAttribute("user", session.getAttribute("user"));
        List<Subject> subjects = subjectService.findAllSubjects();
        model.addAttribute("subjects", subjects);
        return "admin/subjects";
    }

    // 과목 추가 폼 보여주기
    @GetMapping("/admin/subjects/add")
    public String showAddSubjectForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }

        model.addAttribute("user", session.getAttribute("user"));
        return "admin/subject-add";
    }

    // 과목 추가 처리
    @PostMapping("/admin/subjects/add")
    public String addSubject(@ModelAttribute Subject subject,
                             @RequestParam("file") MultipartFile file,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) throws IOException {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }

        if (!file.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path savePath = Paths.get(uploadDir, fileName);

            file.transferTo(savePath.toFile());
            subject.setSyllabusPath(fileName);
        }

        subjectService.addSubject(subject);
        return "redirect:/admin/subjects";
    }

    // 과목 수정 폼 보여주기
    @GetMapping("/admin/subjects/edit/{subjectCode}")
    public String showEditSubjectForm(@PathVariable String subjectCode,
                                      Model model,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }

        model.addAttribute("user", session.getAttribute("user"));
        Subject subject = subjectService.findSubjectByCode(subjectCode);
        model.addAttribute("subject", subject);
        return "admin/subject-edit";
    }

    // 과목 수정 처리
    @PostMapping("/admin/subjects/edit")
    public String editSubject(@ModelAttribute Subject subject,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }

        subjectService.updateSubject(subject);
        return "redirect:/admin/subjects";
    }

    // 과목 삭제
    @PostMapping("/admin/subjects/delete/{subjectCode}")
    public String deleteSubject(@PathVariable String subjectCode,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없습니다.");
            return "redirect:/index";
        }

        subjectService.deleteSubject(subjectCode);
        return "redirect:/admin/subjects";
    }

    // 강의계획서 다운로드
    @GetMapping("/admin/subjects/download/{subjectCode}")
    public ResponseEntity<Resource> downloadSyllabus(@PathVariable String subjectCode) throws IOException {
        Subject subject = subjectService.findSubjectByCode(subjectCode);

        if (subject == null || subject.getSyllabusPath() == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = Paths.get("uploads", subject.getSyllabusPath());
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // 🛡 세션에서 관리자 권한 확인하는 메서드
    private boolean isAdmin(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return false;
        }

        String role = (String) session.getAttribute("role");
        return "ADMIN".equals(role);
    }
}
