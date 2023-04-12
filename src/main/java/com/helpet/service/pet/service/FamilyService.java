package com.helpet.service.pet.service;

import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.dto.request.AddFamilyMemberRequest;
import com.helpet.service.pet.dto.request.AddPetToFamilyRequest;
import com.helpet.service.pet.dto.request.CreateFamilyRequest;
import com.helpet.service.pet.dto.request.UpdateFamilyRequest;
import com.helpet.service.pet.service.error.ConflictLocalizedError;
import com.helpet.service.pet.service.error.ForbiddenLocalizedError;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.storage.model.Account;
import com.helpet.service.pet.storage.model.Family;
import com.helpet.service.pet.storage.model.Pet;
import com.helpet.service.pet.storage.repository.FamilyMemberRepository;
import com.helpet.service.pet.storage.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FamilyService {
    private final AccountService accountService;

    private final PetService petService;

    private final FamilyRepository familyRepository;

    private final FamilyMemberRepository familyMemberRepository;

    @Autowired
    public FamilyService(AccountService accountService,
                         PetService petService,
                         FamilyRepository familyRepository,
                         FamilyMemberRepository familyMemberRepository) {
        this.accountService = accountService;
        this.petService = petService;
        this.familyRepository = familyRepository;
        this.familyMemberRepository = familyMemberRepository;
    }

    public List<Family> getUserFamilies(UUID userId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        return familyRepository.findAllByMembersIdOrderByName(userId);
    }

    public Family getUserFamily(UUID userId, UUID familyId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        return familyRepository.findFamilyByMembersIdAndId(userId, familyId)
                               .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_FAMILY));
    }

    public Family getUserFamilyWithMembers(UUID userId, UUID familyId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        return familyRepository.findFamilyWithMembersByMembersIdAndId(userId, familyId)
                               .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_FAMILY));
    }

    public Family getUserFamilyWithPets(UUID userId, UUID familyId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        return familyRepository.findFamilyWithPetsByMembersIdAndId(userId, familyId)
                               .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_FAMILY));
    }

    public List<Account> getFamilyMembers(UUID userId, UUID familyId) throws NotFoundLocalizedException {
        return getUserFamilyWithMembers(userId, familyId).getMembers()
                                                         .stream()
                                                         .sorted(Comparator.comparing(Account::getName))
                                                         .toList();
    }

    public List<Pet> getFamilyPets(UUID userId, UUID familyId) throws NotFoundLocalizedException {
        return getUserFamilyWithPets(userId, familyId).getPets()
                                                      .stream()
                                                      .sorted(Comparator.comparing(Pet::getName))
                                                      .toList();
    }

    public Family createFamily(UUID userId, CreateFamilyRequest familyInfo) throws NotFoundLocalizedException {
        Account createdBy = accountService.getAccount(userId);

        Family newFamily = Family.builder()
                                 .name(familyInfo.getName())
                                 .members(Set.of(createdBy))
                                 .createdBy(createdBy)
                                 .build();

        return familyRepository.save(newFamily);
    }


    public Family updateFamily(UUID userId, UUID familyId, UpdateFamilyRequest familyInfo) {
        Family family = getUserFamily(userId, familyId);

        family.setName(familyInfo.getName());

        return familyRepository.save(family);
    }

    public void deleteFamily(UUID userId, UUID familyId) {
        Family family = getUserFamily(userId, familyId);

        familyRepository.delete(family);
    }

    public void addFamilyMember(UUID userId,
                                UUID familyId,
                                AddFamilyMemberRequest familyMemberInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        Family userFamily = getUserFamilyWithMembers(userId, familyId);
        if (!userFamily.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.ONLY_FAMILY_OWNER_CAN_ADD_MEMBER);
        }

        Account member = accountService.getAccountByUsername(familyMemberInfo.getUsername());

        userFamily.getMembers().add(member);

        familyRepository.save(userFamily);
    }

    public void removeFamilyMember(UUID userId,
                                   UUID familyId,
                                   String memberUsername) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        Family userFamily = getUserFamilyWithMembers(userId, familyId);
        if (!userFamily.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.ONLY_FAMILY_OWNER_CAN_REMOVE_MEMBER);
        }

        Account member = accountService.getAccountByUsername(memberUsername);
        userFamily.getMembers().remove(member);
        familyRepository.save(userFamily);

        for (Pet memberPet : petService.getUserPets(member.getId())) {
            if (Objects.nonNull(memberPet.getFamily()) && memberPet.getFamily().getId().equals(familyId)) {
                memberPet.setFamily(null);
                petService.savePet(memberPet);
            }
        }
    }

    public void addPetToFamily(UUID userId,
                               UUID familyId,
                               AddPetToFamilyRequest petInfo) throws NotFoundLocalizedException, ConflictLocalizedException {
        Pet pet = petService.getUserPet(userId, petInfo.getPetId());

        if (Objects.nonNull(pet.getFamily())) {
            throw new ConflictLocalizedException(ConflictLocalizedError.PET_ALREADY_HAS_FAMILY);
        }

        Family userFamily = getUserFamilyWithPets(userId, familyId);

        pet.setFamily(userFamily);
        userFamily.getPets().add(pet);

        familyRepository.save(userFamily);
    }

    public void removePetFromFamily(UUID userId, UUID familyId, UUID petId) throws NotFoundLocalizedException {
        Family userFamily = getUserFamilyWithPets(userId, familyId);

        Pet pet = petService.getUserPet(userId, petId);

        if (!userFamily.getPets().contains(pet)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.FAMILY_DOES_NOT_HAVE_THIS_PET);
        }

        pet.setFamily(null);
        userFamily.getPets().remove(pet);

        familyRepository.save(userFamily);
    }

    public List<Account> getAllFamiliesMembers(UUID userId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        return familyMemberRepository.findAllFamilyMembersAssociatedWithUser(userId);
    }
}
