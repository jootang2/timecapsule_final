package com.jootang2.timecapsule.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {
    @Size(min = 3 , max = 25)
    @NotEmpty(message = "사용자ID는 필수입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수입니다.")
    private String passwordConfirm;

    @Email
    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;
}

