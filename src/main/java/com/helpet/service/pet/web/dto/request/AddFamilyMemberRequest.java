package com.helpet.service.pet.web.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AddFamilyMemberRequest {
    private UUID memberId;
}
