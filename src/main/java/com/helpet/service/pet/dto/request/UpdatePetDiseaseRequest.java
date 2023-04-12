package com.helpet.service.pet.dto.request;

import com.helpet.validation.annotation.NotBlankOrNull;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class UpdatePetDiseaseRequest {
    @NotBlank(message = "{validations.not-blank.name-cannot-be-blank}")
    private String diseaseName;

    @NotBlankOrNull(message = "{validations.not-blank-or-null.comment-cannot-be-blank}")
    private String comment;

    @NotNull(message = "{validations.not-null.date-of-illness-cannot-be-null}")
    @PastOrPresent(message = "{validations.past-or-present.date-of-illness-cannot-be-in-future}")
    private LocalDate gotSickOn;

    @PastOrPresent(message = "{validations.past-or-present.recovery-date-cannot-be-in-future}")
    private LocalDate recoveredOn;

    @AssertTrue(message = "{validations.assert-true.recovery-date-cannot-be-earlier-than-date-of-illness}")
    private boolean isDateOfIllnessEarlierThanRecoveryDate() {
        return Objects.isNull(recoveredOn) || !recoveredOn.isBefore(gotSickOn);
    }
}
