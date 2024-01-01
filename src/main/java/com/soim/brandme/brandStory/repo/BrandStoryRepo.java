package com.soim.brandme.brandStory.repo;

import com.soim.brandme.brandStory.entity.BrandStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandStoryRepo extends JpaRepository<BrandStory, Long>{
}
