package com.helpet.service.pet.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class PetFeatureResponse {
    private UUID id;

    private String name;

    private String description;

    private OffsetDateTime createdAt;
}
