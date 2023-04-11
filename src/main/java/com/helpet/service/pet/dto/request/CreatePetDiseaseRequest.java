package com.helpet.service.pet.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePetDiseaseRequest {
    private String diseaseName;

    private String comment;

    private LocalDate gotSickOn;

    private LocalDate recoveredOn;
}
