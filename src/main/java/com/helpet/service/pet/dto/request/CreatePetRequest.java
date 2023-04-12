package com.helpet.service.pet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePetRequest {
    @NotBlank(message = "{validations.not-blank.pet-name-cannot-be-blank}")
    private String name;

    private Integer petCategoryId;
}
