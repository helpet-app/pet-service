package com.helpet.service.pet.service;

import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.service.error.ForbiddenLocalizedError;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.store.model.Family;
import com.helpet.service.pet.store.model.User;
import com.helpet.service.pet.store.repository.FamilyRepository;
import com.helpet.service.pet.web.dto.request.AddFamilyMemberRequest;
import com.helpet.service.pet.web.dto.request.CreateFamilyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class FamilyService {
    private final UserService userService;

    private final FamilyRepository familyRepository;

    @Autowired
    public FamilyService(UserService userService, FamilyRepository familyRepository) {
        this.userService = userService;
        this.familyRepository = familyRepository;
    }

    public Family getFamily(UUID familyId) throws NotFoundLocalizedException {
        return familyRepository.findFamilyById(familyId)
                               .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.FAMILY_DOES_NOT_EXIST));
    }

    public boolean familyHasMember(UUID familyId, UUID memberId) {
        return familyRepository.existsByIdAndMembersId(familyId, memberId);
    }

    public List<Family> getUserFamilies(UUID userId) {
        return familyRepository.findAllByMembersId(userId);
    }

    public Family getUserFamily(UUID userId, UUID familyId) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!familyHasMember(familyId, userId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_FAMILY_MEMBER);
        }

        return getFamily(familyId);
    }

    public Family createFamily(UUID userId, CreateFamilyRequest familyInfo) throws NotFoundLocalizedException {
        User member = userService.getUser(userId);

        Family newFamily = Family.builder()
                                 .name(familyInfo.getName())
                                 .members(Set.of(member))
                                 .build();

        return familyRepository.save(newFamily);
    }

    public void addFamilyMember(UUID userId,
                                UUID familyId,
                                AddFamilyMemberRequest familyMemberInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        User member = userService.getUser(familyMemberInfo.getMemberId());

        Family userFamily = getUserFamily(userId, familyId);
        userFamily.getMembers().add(member);

        familyRepository.save(userFamily);
    }
}
