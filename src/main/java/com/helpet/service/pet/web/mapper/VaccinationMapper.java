package com.helpet.service.pet.web.mapper;

import com.helpet.service.pet.store.model.Vaccination;
import com.helpet.service.pet.web.dto.response.VaccinationResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VaccinationMapper extends ClassMapper<Vaccination, VaccinationResponse> {
}
