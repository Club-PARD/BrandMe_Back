package com.soim.brandme.prompt.repo;

import com.soim.brandme.prompt.entity.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptRepo extends JpaRepository<Prompt, Long> {
}
