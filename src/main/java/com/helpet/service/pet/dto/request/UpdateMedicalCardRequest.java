package com.helpet.service.pet.dto.request;

import com.helpet.service.pet.storage.model.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateMedicalCardRequest {
    private String name;

    private String avatarUrl;

    private Gender gender;

    private LocalDate dateOfBirth;

    private Boolean isSpayedOrNeutered;

    private String chipNumber;

    private Integer petCategoryId;

    private Integer speciesId;
}
