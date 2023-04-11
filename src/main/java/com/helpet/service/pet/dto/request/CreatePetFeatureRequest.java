package com.helpet.service.pet.dto.request;

import lombok.Data;

@Data
public class CreatePetFeatureRequest {
    private String name;

    private String description;
}
