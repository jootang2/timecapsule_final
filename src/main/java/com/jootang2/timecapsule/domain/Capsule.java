package com.jootang2.timecapsule.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Capsule extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAPSULE_ID")
    private Long id;

    @Column(name = "CAPSULE_TITLE")
    private String capsuleTitle;

    @Column(name = "CAPSULE_PASSWORD")
    private String capsulePassWord;

    @Column(name = "CAPSULE_SUMAARY")
    private String capsuleSummary;

    @Column(name = "CAPSULE_SATATUS")
    private String capsuleStatus;

    @Column(name = "CAPSULE_STORAGE_DATE")
    private String capsuleStorageDate;

    @Column(name = "CAPUSLE_RESERVATION_DATE")
    private String capsuleReservationDate;

    @Column(name = "CAPSULE_TO_USER_MAIL")
    private String capsuleToUserMail;

    @Column(name = "CAPSULE_ACCESSKEY")
    private String capsuleAccessKey;

    @Column(name = "CAPSULE_MESSAGE")
    private String capsuleMessage;

    @ManyToOne
    @JoinColumn(name = "USER_UUID")
    private SiteUser user;

}
