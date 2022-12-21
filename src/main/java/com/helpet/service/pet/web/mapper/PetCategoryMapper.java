package com.helpet.service.pet.web.mapper;

import com.helpet.service.pet.store.model.PetCategory;
import com.helpet.service.pet.web.dto.response.PetCategoryResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetCategoryMapper extends ClassMapper<PetCategory, PetCategoryResponse> {
}
