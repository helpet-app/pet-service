package com.helpet.service.pet.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePetVaccinationRequest {
    private String vaccinationName;

    private String comment;

    private LocalDate vaccinatedOn;
}
