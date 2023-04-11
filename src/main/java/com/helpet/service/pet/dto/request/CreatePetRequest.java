package com.helpet.service.pet.dto.request;

import lombok.Data;

@Data
public class CreatePetRequest {
    private String name;

    private Integer petCategoryId;
}
