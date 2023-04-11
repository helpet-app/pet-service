package com.helpet.service.pet.mapper;

import com.helpet.service.pet.dto.response.MedicalCardResponse;
import com.helpet.service.pet.storage.model.Pet;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalCardMapper extends ClassMapper<Pet, MedicalCardResponse> {
}
