package com.helpet.service.pet.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class PetVaccinationResponse {
    private UUID id;

    private String vaccinationName;

    private String comment;

    private LocalDate vaccinatedOn;

    private OffsetDateTime createdAt;
}
