package com.gsa.pricing.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class PricingResponse {

    private Long id;
    private Long showId;
    private String seatType;
    private BigDecimal price;
    private String currency;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
}
