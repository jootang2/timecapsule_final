package com.jootang2.timecapsule.domain;

import com.jootang2.timecapsule.enumType.BoardCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "BOARD_CATEGORY")
    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    @Column(name = "BOARD_TITLE")
    private String boardTitle;

    @Column(name = "BOARD_CONTENT", columnDefinition = "TEXT")
    private String boardContent;

    @OneToOne
    @JoinColumn(name = "CAPSULE_ID")
    private Capsule capsule;

    @Column(name = "placeX", columnDefinition = "TEXT")
    private String placeX;

    @Column(name = "placeY", columnDefinition = "TEXT")
    private String placeY;

    @ManyToOne
    @JoinColumn(name = "USER_UUID")
    private SiteUser user;

}
