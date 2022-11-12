package com.jootang2.timecapsule.service;

import com.jootang2.timecapsule.domain.SiteUser;
import com.jootang2.timecapsule.dto.UserDto;
import com.jootang2.timecapsule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(UserDto userDto){
        SiteUser user = new SiteUser();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setNumberOfCapsule(0);
        userRepository.save(user);
    }

}
