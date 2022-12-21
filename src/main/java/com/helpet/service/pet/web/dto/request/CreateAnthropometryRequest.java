package com.helpet.service.pet.web.dto.request;

import lombok.Data;

@Data
public class CreateAnthropometryRequest {
    private Double height;

    private Double weight;

    private String comment;
}
