package com.helpet.service.pet.web.dto.response;

import com.helpet.service.pet.store.model.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MedicalCardResponse {
    private UUID id;

    private String name;

    private String avatarUrl;

    private Sex sex;

    private LocalDate dateOfBirth;

    private Boolean isSterilized;

    private String chipNumber;

    private PetCategoryResponse petCategory;

    private SpeciesResponse species;
}
