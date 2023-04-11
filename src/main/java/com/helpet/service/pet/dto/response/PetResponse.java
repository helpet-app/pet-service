package com.helpet.service.pet.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class PetResponse {
    private UUID id;

    private String name;

    private String avatarUrl;

    private PetFamilyResponse family;

    private PetCategoryResponse petCategory;

    private SpeciesResponse species;
}
