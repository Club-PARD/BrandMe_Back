package com.soim.brandme.prompt.controller;

import com.soim.brandme.prompt.dto.PromptDto;
import com.soim.brandme.prompt.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PromptController {
    private final PromptService promptService;

    @GetMapping("/prompt")
    public PromptDto getPrompt(){
        return promptService.getPrompt();
    }

}
