package com.helpet.service.pet.dto.request;

import com.helpet.validation.annotation.NotBlankOrNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePetAnthropometryRequest {
    @NotNull(message = "{validations.not-null.height-cannot-be-null}")
    @Min(value = 0, message = "{validations.min.height-must-be-at-least-n}")
    private Double height;

    @NotNull(message = "{validations.not-null.weight-cannot-be-null}")
    @Min(value = 0, message = "{validations.min.weight-must-be-at-least-n}")
    private Double weight;

    @NotBlankOrNull(message = "{validations.not-blank-or-null.comment-cannot-be-blank}")
    private String comment;
}
