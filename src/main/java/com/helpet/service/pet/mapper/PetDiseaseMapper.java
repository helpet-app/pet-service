package com.helpet.service.pet.mapper;

import com.helpet.service.pet.dto.response.PetDiseaseResponse;
import com.helpet.service.pet.storage.model.PetDisease;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetDiseaseMapper extends ClassMapper<PetDisease, PetDiseaseResponse> {
}
