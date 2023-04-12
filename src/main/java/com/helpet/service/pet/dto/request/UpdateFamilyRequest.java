package com.helpet.service.pet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateFamilyRequest {
    @NotBlank(message = "{validations.not-blank.name-cannot-be-blank}")
    private String name;
}
