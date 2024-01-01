package com.soim.brandme.brandStory.controller;

import com.soim.brandme.brandStory.dto.BrandStoryDto;
import com.soim.brandme.brandStory.service.BrandStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BrandStoryController {
    private final BrandStoryService brandStoryService;
    @PostMapping("/{userId}/{chatRoomId}/brandStory")
    public ResponseEntity<BrandStoryDto> saveBrandStory(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody BrandStoryDto brandStoryDto){
        BrandStoryDto ret = brandStoryService.saveBrandStory(userId,chatRoomId,brandStoryDto);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
