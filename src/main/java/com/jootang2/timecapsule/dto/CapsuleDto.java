package com.jootang2.timecapsule.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CapsuleDto {
    @NotEmpty(message = "캡슐제목은 필수입니다.")
    private String capsuleTitle;

    @NotEmpty(message = "캡슐 비밀번호는 필수입니다.")
    private String capsulePassword;

    @Size(max = 25 , message = "최대 25글자 까지 등록할 수 있습니다.")
    @NotEmpty(message = "한 줄 소개는 필수입니다.")
    private String capsuleSummary;

    private String capsuleReservationDate;
    private String capsuleToUserMail;
    private String capsuleReservationTime;
    private String capsuleMessage;
}
