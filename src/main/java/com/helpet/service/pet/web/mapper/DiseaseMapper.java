package com.helpet.service.pet.web.mapper;

import com.helpet.service.pet.store.model.Disease;
import com.helpet.service.pet.web.dto.response.DiseaseResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiseaseMapper extends ClassMapper<Disease, DiseaseResponse> {
}
