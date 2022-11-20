package com.jootang2.timecapsule.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Notice extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOTICE_CATEGORY")
    private String category;

    @Column(name = "NOTICE_SUBJECT")
    private String subject;

    @Column(name = "NOTICE_CONTENT", columnDefinition = "TEXT")
    private String content;
}
