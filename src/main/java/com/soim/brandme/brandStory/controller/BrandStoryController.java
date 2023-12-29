package com.soim.brandme.brandStory.controller;

import com.soim.brandme.brandStory.service.BrandStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BrandStoryController {
    private final BrandStoryService brandStoryService;

//    @PostMapping("/{userId}/{chatRoomId}/addResources")
//    public List<String> addResource(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody List<String> resorces){
//
//
//    }
}
