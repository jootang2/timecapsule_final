package com.jootang2.timecapsule.service;

import com.jootang2.timecapsule.domain.Capsule;
import com.jootang2.timecapsule.domain.SiteUser;
import com.jootang2.timecapsule.dto.CapsuleDto;
import com.jootang2.timecapsule.repository.CapsuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CapsuleService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CapsuleRepository capsuleRepository;

    public void create(CapsuleDto capsuleDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        SiteUser user = userService.findByName(userName);
        Capsule capsule = new Capsule();
        capsule.setCapsuleStatus("writing");
        capsule.setCapsuleTitle(capsuleDto.getCapsuleTitle());
        capsule.setCapsuleAccessKey("defaultKey");
        capsule.setCapsuleSummary(capsuleDto.getCapsuleSummary());
        capsule.setCapsulePassWord(passwordEncoder.encode(capsuleDto.getCapsulePassword()));
        capsule.setUser(user);
        user.setNumberOfCapsule(user.getNumberOfCapsule()+1);
        capsuleRepository.save(capsule);
    }

    public List<Capsule> findAll() {
        return capsuleRepository.findAll();
    }

    public List<Capsule> findByUser(SiteUser user) {
        return capsuleRepository.findByUser(user);
    }

    public Capsule findById(Long capsuleId) {
        return capsuleRepository.findById(capsuleId).orElseThrow(null);
    }

    public void storageCapsule(Long capsuleId, CapsuleDto capsuleDto) {
        //랜덤 문자열 생성
        String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int alphaNumLength = alphaNum.length();
        Random random = new Random();
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < 20; i++) {
            code.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
        }
        //
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        String formattedNow = formatter.format(now);
        String reservationDate = capsuleDto.getCapsuleReservationDate().split("-")[0] +
                "년 " +
                capsuleDto.getCapsuleReservationDate().split("-")[1] +
                "월 " +
                capsuleDto.getCapsuleReservationDate().split("-")[2] +
                "일 " +
                capsuleDto.getCapsuleReservationTime().split(":")[0] +
                "시 " +
                capsuleDto.getCapsuleReservationTime().split(":")[1] +
                "분";
        Capsule capsule = findById(capsuleId);
        capsule.setCapsuleStatus("storage");
        capsule.setCapsuleStorageDate(formattedNow);
        capsule.setCapsuleReservationDate(reservationDate);
        capsule.setCapsuleToUserMail(capsuleDto.getCapsuleToUserMail());
        capsule.setCapsuleMessage(capsuleDto.getCapsuleMessage());
        capsule.setCapsuleAccessKey(code.toString());
        capsule.setCapsulePassWord(passwordEncoder.encode(code.toString()));
        capsuleRepository.save(capsule);
    }

    public void delete(Long capsuleId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        SiteUser user = userService.findByName(userName);
        Capsule capsule = findById(capsuleId);
        user.setNumberOfCapsule(user.getNumberOfCapsule()-1);
        capsuleRepository.delete(capsule);
    }

    public void setNewPassword(Capsule capsule, String newPassword) {
        capsule.setCapsulePassWord(passwordEncoder.encode(newPassword));
        capsuleRepository.save(capsule);
    }
}
