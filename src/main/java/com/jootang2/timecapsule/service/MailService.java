package com.jootang2.timecapsule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendPasswordEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("joohwan.song15b@gmail.com");
        message.setTo(email);
        message.setSubject("TimeCapsule 회원님의 임시 비밀번호입니다.");
        message.setText("임시비밀번호는 " + newPassword + " 입니다.\n" +
                "비밀번호를 반드시 변경해주세요.");
        mailSender.send(message);
    }
}
