package com.helpet.service.pet.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AddFamilyMemberRequest {
    private UUID memberId;
}
