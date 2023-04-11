package com.helpet.service.pet.mapper;

import com.helpet.service.pet.dto.response.SpeciesResponse;
import com.helpet.service.pet.storage.model.Species;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeciesMapper extends ClassMapper<Species, SpeciesResponse> {
}
