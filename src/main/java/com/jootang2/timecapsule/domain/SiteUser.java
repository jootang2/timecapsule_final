package com.jootang2.timecapsule.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SiteUser extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name="USER_NAME")
    private String userName;

    @Column(name="USER_PASSWORD")
    private String userPassword;

    @Column(unique = true, name="USER_EMAIL")
    private String userEmail;

    @Column(name = "NUMBER_OF_CAPSULE")
    private int numberOfCapsule;
}