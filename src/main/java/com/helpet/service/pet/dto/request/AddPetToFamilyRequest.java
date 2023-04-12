package com.helpet.service.pet.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AddPetToFamilyRequest {
    @NotNull(message = "{validations.not-null.pet-id-cannot-be-null}")
    private UUID petId;
}
