package com.gsa.pricing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "show_pricing",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_show_seat_type",
                        columnNames = {"show_id", "seat_type"}
                )
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowPricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long showId;

    private String seatType;   // GOLD, PREMIUM, etc

    private BigDecimal price;

    private String currency;

    private LocalDateTime effectiveFrom;

    private LocalDateTime effectiveTo;
}
