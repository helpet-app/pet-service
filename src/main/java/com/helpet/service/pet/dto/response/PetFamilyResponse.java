package com.helpet.service.pet.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class PetFamilyResponse {
    private UUID id;

    private String name;
}
