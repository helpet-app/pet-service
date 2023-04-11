package com.helpet.service.pet.dto.response;

import com.helpet.service.pet.storage.model.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MedicalCardResponse {
    private UUID id;

    private String name;

    private String avatarUrl;

    private Gender gender;

    private LocalDate dateOfBirth;

    private Boolean isSpayedOrNeutered;

    private String chipNumber;

    private PetCategoryResponse petCategory;

    private SpeciesResponse species;
}
