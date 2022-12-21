package com.helpet.service.pet.web.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateDiseaseRequest {
    private String diseaseName;

    private String comment;

    private LocalDate gotSickOn;

    private LocalDate recoveredOn;
}
