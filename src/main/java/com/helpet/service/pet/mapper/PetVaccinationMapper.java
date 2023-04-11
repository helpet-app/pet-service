package com.helpet.service.pet.mapper;

import com.helpet.service.pet.dto.response.PetVaccinationResponse;
import com.helpet.service.pet.storage.model.PetVaccination;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetVaccinationMapper extends ClassMapper<PetVaccination, PetVaccinationResponse> {
}
