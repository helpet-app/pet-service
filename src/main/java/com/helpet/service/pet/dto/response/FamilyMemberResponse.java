package com.helpet.service.pet.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class FamilyMemberResponse {
    private UUID id;

    private String username;

    private String name;

    private String avatarUrl;
}
