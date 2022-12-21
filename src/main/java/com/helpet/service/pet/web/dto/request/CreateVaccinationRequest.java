package com.helpet.service.pet.web.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateVaccinationRequest {
    private String vaccinationName;

    private String comment;

    private LocalDate vaccinatedOn;
}
