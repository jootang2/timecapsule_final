package com.jootang2.timecapsule.service;

import com.jootang2.timecapsule.domain.SiteUser;
import com.jootang2.timecapsule.dto.UserDto;
import com.jootang2.timecapsule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public void create(UserDto userDto) {
        SiteUser user = new SiteUser();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setNumberOfCapsule(0);
        userRepository.save(user);
    }

    public String findNameByEmail(String email) {
        List<SiteUser> userList = userRepository.findAll();
        for (SiteUser user : userList) {
            if (user.getEmail().equals(email)) {
                return user.getName();
            }
        }

        return "등록된 ID가 없습니다.";
    }

    public String resetUserPassword(String userName, String email) {
        List<SiteUser> userList = userRepository.findAll();
        for (SiteUser user : userList) {
            if(user.getName().equals(userName) && user.getEmail().equals(email)){
                //랜덤 문자열 생성
                String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                int alphaNumLength = alphaNum.length();
                Random random = new Random();
                StringBuffer code = new StringBuffer();
                for (int i = 0; i < 20; i++) {
                    code.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
                }
                //
                user.setPassword(passwordEncoder.encode(code.toString()));
                userRepository.save(user);
                mailService.sendPasswordEmail(email, code.toString());
                return "가입하신 email로 임시비밀번호를 발송했습니다.";
            }
        }
        return "등록된 계정이 없습니다.";
    }

    public SiteUser findByName(String userName) {
        return userRepository.findByName(userName).orElseThrow(null);
    }
}
