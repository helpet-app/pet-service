package com.helpet.service.pet.dto.request;

import com.helpet.validation.annotation.NotBlankOrNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePetVaccinationRequest {
    @NotBlank(message = "{validations.not-blank.name-cannot-be-blank}")
    private String vaccinationName;

    @NotBlankOrNull(message = "{validations.not-blank-or-null.comment-cannot-be-blank}")
    private String comment;

    @NotNull(message = "{validations.not-null.date-of-vaccination-cannot-be-null}")
    @PastOrPresent(message = "{validations.past-or-present.date-of-vaccination-cannot-be-in-future}")
    private LocalDate vaccinatedOn;
}
