package com.jootang2.timecapsule.service;

import com.jootang2.timecapsule.domain.Notice;
import com.jootang2.timecapsule.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public Notice findById(Long noticeId) {
        return noticeRepository.findById(noticeId).orElseThrow(null);
    }

    public void addHit(Notice notice) {
        notice.setHit(notice.getHit()+1);
        noticeRepository.save(notice);

    }
}
