package com.helpet.service.pet.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class PetDiseaseResponse {
    private UUID id;

    private String diseaseName;

    private String comment;

    private LocalDate gotSickOn;

    private LocalDate recoveredOn;

    private OffsetDateTime createdAt;
}
