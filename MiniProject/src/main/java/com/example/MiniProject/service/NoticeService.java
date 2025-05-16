package com.example.MiniProject.service;

import com.example.MiniProject.entity.Notice;
import com.example.MiniProject.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public Notice findById(Long id) {
        return noticeRepository.findById(id).orElse(null);
    }

    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }

    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
