package com.gsa.pricing.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreatePricingRequest {

    @NotNull
    private Long showId;

    @NotBlank
    private String seatType;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    private String currency;

    @NotNull
    private LocalDateTime effectiveFrom;

    private LocalDateTime effectiveTo;
}
