package com.helpet.service.pet.web.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class DiseaseResponse {
    private UUID id;

    private String diseaseName;

    private String comment;

    private LocalDate gotSickOn;

    private LocalDate recoveredOn;
}
