package com.helpet.service.pet.mapper;

import com.helpet.service.pet.dto.response.PetAnthropometryResponse;
import com.helpet.service.pet.storage.model.PetAnthropometry;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetAnthropometryMapper extends ClassMapper<PetAnthropometry, PetAnthropometryResponse> {
}
