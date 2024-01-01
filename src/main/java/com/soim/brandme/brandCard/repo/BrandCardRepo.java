package com.soim.brandme.brandCard.repo;

import com.soim.brandme.brandCard.entity.BrandCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandCardRepo extends JpaRepository<BrandCard, Long>{
}
