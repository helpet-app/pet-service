package com.helpet.service.pet.mapper;

import com.helpet.service.pet.dto.response.FamilyMemberResponse;
import com.helpet.service.pet.storage.model.Account;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FamilyMemberMapper extends ClassMapper<Account, FamilyMemberResponse> {
}
