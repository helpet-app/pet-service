package com.helpet.service.pet.web.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class VaccinationResponse {
    private UUID id;

    private String vaccinationName;

    private String comment;

    private LocalDate vaccinatedOn;
}
