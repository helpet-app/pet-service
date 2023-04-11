package com.helpet.service.pet.dto.request;

import lombok.Data;

@Data
public class UpdatePetAnthropometryRequest {
    private Double height;

    private Double weight;

    private String comment;
}
