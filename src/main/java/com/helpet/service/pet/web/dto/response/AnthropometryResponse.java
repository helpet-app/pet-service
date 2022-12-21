package com.helpet.service.pet.web.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class AnthropometryResponse {
    private UUID id;

    private Double height;

    private Double weight;

    private String comment;

    private OffsetDateTime createdAt;
}
