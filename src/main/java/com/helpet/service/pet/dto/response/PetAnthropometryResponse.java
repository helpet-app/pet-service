package com.helpet.service.pet.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class PetAnthropometryResponse {
    private UUID id;

    private Double height;

    private Double weight;

    private String comment;

    private OffsetDateTime createdAt;
}
