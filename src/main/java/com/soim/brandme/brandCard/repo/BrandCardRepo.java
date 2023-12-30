package com.soim.brandme.brandCard.repo;

import com.soim.brandme.brandCard.entity.BrandCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandCardRepo extends JpaRepository<BrandCard, Long> {
}
