package com.jootang2.timecapsule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {

    @GetMapping("/myPlace")
    public String myPlace(){
        return "map/myPlace";
    }

    @GetMapping("/myStoragePlace")
    public String myStoragePlace(){
        return "map/myStoragePlace";
    }
}
