package com.gsa.pricing.service;

import com.gsa.pricing.dto.CreatePricingRequest;
import com.gsa.pricing.dto.PricingResponse;
import com.gsa.pricing.entity.ShowPricing;
import com.gsa.pricing.exception.PricingNotFoundException;
import com.gsa.pricing.repository.ShowPricingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final ShowPricingRepository repo;

    public PricingResponse create(CreatePricingRequest req) {

        ShowPricing pricing = ShowPricing.builder()
                .showId(req.getShowId())
                .seatType(req.getSeatType())
                .price(req.getPrice())
                .currency(req.getCurrency())
                .effectiveFrom(req.getEffectiveFrom())
                .effectiveTo(req.getEffectiveTo())
                .build();

        return map(repo.save(pricing));
    }

    public PricingResponse update(Long id,
                                  CreatePricingRequest req) {

        ShowPricing pricing = repo.findById(id)
                .orElseThrow(() ->
                        new PricingNotFoundException(
                                "Pricing not found " + id));

        pricing.setPrice(req.getPrice());
        pricing.setCurrency(req.getCurrency());
        pricing.setEffectiveFrom(req.getEffectiveFrom());
        pricing.setEffectiveTo(req.getEffectiveTo());

        return map(repo.save(pricing));
    }

    public List<PricingResponse> getByShow(Long showId) {
        return repo.findByShowId(showId)
                .stream()
                .map(this::map)
                .toList();
    }

    public PricingResponse getBySeatType(Long showId,
                                         String seatType) {

        ShowPricing pricing =
                repo.findByShowIdAndSeatType(showId, seatType)
                        .orElseThrow(() ->
                                new PricingNotFoundException(
                                        "Pricing not found"));

        return map(pricing);
    }

    private PricingResponse map(ShowPricing p) {
        return PricingResponse.builder()
                .id(p.getId())
                .showId(p.getShowId())
                .seatType(p.getSeatType())
                .price(p.getPrice())
                .currency(p.getCurrency())
                .effectiveFrom(p.getEffectiveFrom())
                .effectiveTo(p.getEffectiveTo())
                .build();
    }
}
