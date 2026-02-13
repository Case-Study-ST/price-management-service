package com.gsa.pricing.controller;

import com.gsa.pricing.dto.CreatePricingRequest;
import com.gsa.pricing.dto.PricingResponse;
import com.gsa.pricing.service.PricingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing")
@RequiredArgsConstructor
@Tag(
        name = "Pricing Management",
        description = "Manage seat type pricing per show, including dynamic pricing windows"
)
public class PricingController {

    private final PricingService service;

    @Operation(
            summary = "Create pricing for a show seat type",
            description = """
                    Creates pricing configuration for a seat type in a show.
                    Only one pricing record allowed per show and seat type.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pricing created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "409", description = "Pricing already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PricingResponse> create(
            @Valid
            @RequestBody
            @Schema(description = "Pricing creation request")
            CreatePricingRequest request) {

        return ResponseEntity.ok(service.create(request));
    }

    @Operation(
            summary = "Update pricing configuration",
            description = "Updates price or effective window for an existing pricing record"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pricing updated"),
            @ApiResponse(responseCode = "404", description = "Pricing not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PricingResponse> update(
            @PathVariable Long id,
            @Valid
            @RequestBody
            @Schema(description = "Updated pricing request")
            CreatePricingRequest request) {

        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(
            summary = "Get all pricing for a show",
            description = "Returns pricing for all seat types in a show"
    )
    @ApiResponse(responseCode = "200", description = "Pricing fetched successfully")
    @GetMapping("/show/{showId}")
    public ResponseEntity<List<PricingResponse>> getByShow(
            @PathVariable Long showId) {

        return ResponseEntity.ok(service.getByShow(showId));
    }

    @Operation(
            summary = "Get pricing by seat type",
            description = "Returns price for a specific seat type in a show"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pricing found"),
            @ApiResponse(responseCode = "404", description = "Pricing not found", content = @Content)
    })
    @GetMapping("/show/{showId}/seat-type/{seatType}")
    public ResponseEntity<PricingResponse> getBySeatType(
            @PathVariable Long showId,
            @PathVariable String seatType) {

        return ResponseEntity.ok(
                service.getBySeatType(showId, seatType)
        );
    }
}

