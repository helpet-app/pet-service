package com.helpet.service.pet.web.dto.request;

import com.helpet.service.pet.store.model.Sex;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateMedicalCardRequest {
    private String name;

    private String avatarUrl;

    private Sex sex;

    private LocalDate dateOfBirth;

    private Boolean isSterilized;

    private String chipNumber;

    private Integer petCategoryId;

    private Integer speciesId;
}
