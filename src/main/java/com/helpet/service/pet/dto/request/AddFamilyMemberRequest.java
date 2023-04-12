package com.helpet.service.pet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddFamilyMemberRequest {
    @NotBlank(message = "{validations.not-blank.username-cannot-be-blank}")
    private String username;
}
