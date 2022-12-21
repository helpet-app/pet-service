package com.helpet.service.pet.web.mapper;

import com.helpet.service.pet.store.model.PetFeature;
import com.helpet.service.pet.web.dto.response.PetFeatureResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetFeatureMapper extends ClassMapper<PetFeature, PetFeatureResponse> {
}
