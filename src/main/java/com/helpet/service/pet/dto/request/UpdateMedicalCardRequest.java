package com.helpet.service.pet.dto.request;

import com.helpet.service.pet.storage.model.Gender;
import com.helpet.validation.annotation.NotBlankOrNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateMedicalCardRequest {
    @NotBlank(message = "{validations.not-blank.pet-name-cannot-be-blank}")
    private String name;

    private Gender gender;

    @PastOrPresent(message = "{validations.past-or-present.date-of-birth-cannot-be-in-future}")
    private LocalDate dateOfBirth;

    private Boolean isSpayedOrNeutered;

    @NotBlankOrNull(message = "{validations.not-blank-or-null.chip-number-cannot-be-blank}")
    private String chipNumber;

    private Integer petCategoryId;

    private Integer speciesId;
}
