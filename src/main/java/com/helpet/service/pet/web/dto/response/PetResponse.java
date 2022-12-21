package com.helpet.service.pet.web.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class PetResponse {
    private UUID id;

    private String name;

    private String avatarUrl;

    private FamilyResponse family;

    private PetCategoryResponse petCategory;
}
