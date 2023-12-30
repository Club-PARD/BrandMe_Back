package com.soim.brandme.brandCard.controller;

import com.soim.brandme.brandCard.dto.BrandCardDto;
import com.soim.brandme.brandCard.repo.BrandCardRepo;
import com.soim.brandme.brandCard.service.BrandCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BrandCardController {
    private final BrandCardService brandCardService;

    @PostMapping("/{userId}/{chatRoomId}/addBrandCard")
    public ResponseEntity<BrandCardDto> addBrandCard(@PathVariable Long userId, @PathVariable Long chatRoomId, @RequestBody BrandCardDto brandCard){
        BrandCardDto ret = brandCardService.addBrandCard(userId,chatRoomId,brandCard);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
