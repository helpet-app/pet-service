package com.helpet.service.pet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePetFeatureRequest {
    @NotBlank(message = "{validations.not-blank.name-cannot-be-blank}")
    private String name;

    @NotBlank(message = "{validations.not-blank.description-cannot-be-blank}")
    private String description;
}
