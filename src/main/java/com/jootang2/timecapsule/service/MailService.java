package com.jootang2.timecapsule.service;

import com.jootang2.timecapsule.domain.Capsule;
import com.jootang2.timecapsule.repository.CapsuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final CapsuleRepository capsuleRepository;

    @Scheduled(cron = "0 0/1 * * * *") //1분마다 검사
    public void sendMail() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        String NOW = formatter.format(now);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        List<Capsule> capsuleList = capsuleRepository.findAll();
        for (Capsule capsule : capsuleList) {
            if (capsule.getCapsuleStatus().equals("storage") && capsule.getCapsuleReservationDate().equals(NOW)) {
                StringBuffer sb = new StringBuffer();
                sb.append("<html><body>");
                sb.append("<h1>TimeCapsule을 꺼낼 시간입니다.</h1>\n" +
                        "<center>\n" +
                        "    <img src=\"https://cdn-icons-png.flaticon.com/512/4689/4689650.png\" width=300; height=300></a>\n" +
                        "</center>\n" +
                        "<h3> 타임캡슐 이름 : " + capsule.getCapsuleTitle() + "</h3>\n" +
                        "<h3> 타임캡슐 생성 날짜 : " + capsule.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "</h3>\n" +
                        "<h3>한 줄 메세지 : " + capsule.getCapsuleMessage() + "</h3>\n" +
                        "<a href=http://www.timecapsule.jootang2.com/"+capsule.getId()+"/board/password/"+capsule.getCapsuleAccessKey()+">TimeCapsule 보러가기</a>");
                sb.append("</body></html>");
                String str = sb.toString();
                try {
                    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    messageHelper.setSubject("TimeCapsule을 꺼내볼 시간입니다!");
                    messageHelper.setTo(capsule.getCapsuleToUserMail());
                    messageHelper.setFrom("timecapsule.jootang2@gmail.com");
                    messageHelper.setText(str, true);
                    mailSender.send(mimeMessage);
                    System.out.println("지정된 시간에 메일 전송 완료");
                    System.out.println(capsule.getId());
                    capsule.setCapsuleStatus("complete");
                    capsuleRepository.save(capsule);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void sendPasswordEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("timecapsule.song15b@gmail.com");
        message.setTo(email);
        message.setSubject("TimeCapsule 회원님의 임시 비밀번호입니다.");
        message.setText("임시비밀번호는 " + newPassword + " 입니다.\n" +
                "비밀번호를 반드시 변경해주세요.");
        mailSender.send(message);
    }
}
