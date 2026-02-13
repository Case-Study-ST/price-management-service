package com.gsa.pricing.repository;

import com.gsa.pricing.entity.ShowPricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowPricingRepository
        extends JpaRepository<ShowPricing, Long> {

    List<ShowPricing> findByShowId(Long showId);

    Optional<ShowPricing> findByShowIdAndSeatType(
            Long showId,
            String seatType
    );
}
