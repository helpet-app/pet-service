package com.helpet.service.pet.web.mapper;

import com.helpet.service.pet.store.model.Species;
import com.helpet.service.pet.web.dto.response.SpeciesResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeciesMapper extends ClassMapper<Species, SpeciesResponse> {
}
