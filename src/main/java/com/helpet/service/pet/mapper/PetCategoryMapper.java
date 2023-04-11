package com.helpet.service.pet.mapper;

import com.helpet.service.pet.dto.response.PetCategoryResponse;
import com.helpet.service.pet.storage.model.PetCategory;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetCategoryMapper extends ClassMapper<PetCategory, PetCategoryResponse> {
}
