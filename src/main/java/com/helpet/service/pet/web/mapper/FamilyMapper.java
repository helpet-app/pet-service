package com.helpet.service.pet.web.mapper;

import com.helpet.service.pet.store.model.Family;
import com.helpet.service.pet.web.dto.response.FamilyResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FamilyMapper extends ClassMapper<Family, FamilyResponse> {
}
