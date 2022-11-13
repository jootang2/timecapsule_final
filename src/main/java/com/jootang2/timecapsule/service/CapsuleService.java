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
}
