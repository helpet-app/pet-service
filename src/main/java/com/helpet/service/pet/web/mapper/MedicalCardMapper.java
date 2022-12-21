package com.helpet.service.pet.web.mapper;

import com.helpet.service.pet.store.model.Pet;
import com.helpet.service.pet.web.dto.response.MedicalCardResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalCardMapper extends ClassMapper<Pet, MedicalCardResponse> {
}