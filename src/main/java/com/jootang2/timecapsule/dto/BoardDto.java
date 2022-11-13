package com.jootang2.timecapsule.dto;

import com.jootang2.timecapsule.enumType.BoardCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private String boardTitle;
    private BoardCategory boardCategory;
    private String boardContent;
    private String placeX;
    private String placeY;
}
