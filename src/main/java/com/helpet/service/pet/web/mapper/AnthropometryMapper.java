package com.helpet.service.pet.web.mapper;

import com.helpet.service.pet.store.model.Anthropometry;
import com.helpet.service.pet.web.dto.response.AnthropometryResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnthropometryMapper extends ClassMapper<Anthropometry, AnthropometryResponse> {
}
