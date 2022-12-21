package com.helpet.service.pet.web.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class FamilyResponse {
    private UUID id;

    private String name;
}