package com.helpet.service.pet.mapper;

import com.helpet.service.pet.storage.model.PetFeature;
import com.helpet.service.pet.dto.response.PetFeatureResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetFeatureMapper extends ClassMapper<PetFeature, PetFeatureResponse> {
}
