package com.soim.brandme.prompt.service;

import com.soim.brandme.prompt.dto.PromptDto;
import com.soim.brandme.prompt.repo.PromptRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptService {
    private final PromptRepo promptRepo;

    public PromptDto getPrompt() {
        return PromptDto.builder()
                .questionPrompt(promptRepo.findById(1L).get().getQuestionPrompt())
                .answerPrompt(promptRepo.findById(1L).get().getAnswerPrompt())
                .build();
    }

}
