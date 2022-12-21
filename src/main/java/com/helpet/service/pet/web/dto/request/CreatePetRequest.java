package com.helpet.service.pet.web.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CreatePetRequest {
    private String name;

    private String avatarUrl;

    private UUID familyId;

    private Integer petCategoryId;
}
