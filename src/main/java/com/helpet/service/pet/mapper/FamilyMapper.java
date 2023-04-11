package com.helpet.service.pet.mapper;

import com.helpet.service.pet.dto.response.FamilyResponse;
import com.helpet.service.pet.storage.model.Family;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FamilyMapper extends ClassMapper<Family, FamilyResponse> {
}
