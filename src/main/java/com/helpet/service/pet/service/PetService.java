package com.helpet.service.pet.service;

import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.service.error.ConflictLocalizedError;
import com.helpet.service.pet.service.error.ForbiddenLocalizedError;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.store.model.*;
import com.helpet.service.pet.store.repository.PetRepository;
import com.helpet.service.pet.web.dto.request.AddPetToFamilyRequest;
import com.helpet.service.pet.web.dto.request.CreatePetRequest;
import com.helpet.service.pet.web.dto.request.UpdateMedicalCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PetService {
    private final UserService userService;

    private final FamilyService familyService;

    private final PetCategoryService petCategoryService;

    private final SpeciesService speciesService;

    private final PetRepository petRepository;

    @Autowired
    public PetService(UserService userService,
                      FamilyService familyService,
                      PetCategoryService petCategoryService,
                      SpeciesService speciesService,
                      PetRepository petRepository) {
        this.userService = userService;
        this.familyService = familyService;
        this.petCategoryService = petCategoryService;
        this.speciesService = speciesService;
        this.petRepository = petRepository;
    }

    public Pet getPet(UUID petId) throws NotFoundLocalizedException {
        return petRepository.findPetById(petId).orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST));
    }

    public boolean userIsRelatedToPet(UUID userId, UUID petId) throws NotFoundLocalizedException {
        if (!userService.userExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_EXIST);
        }

        Pet pet = getPet(petId);

        Family family = pet.getFamily();
        if (Objects.isNull(family)) {
            return Objects.equals(pet.getOwner().getId(), userId);
        }

        return familyService.familyHasMember(family.getId(), userId);
    }

    public List<Pet> getUserPets(UUID userId) {
        return petRepository.findAllPetsRelatedToUser(userId);
    }

    public Pet getPet(UUID userId, UUID petId) throws NotFoundLocalizedException {
        if (!userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return petRepository.findPetById(petId)
                            .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST));
    }

    public Pet createPet(UUID userId, CreatePetRequest petInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        User owner = userService.getUser(userId);
        UUID familyId = petInfo.getFamilyId();
        Family family = null;
        if (Objects.nonNull(familyId)) {
            if (!familyService.familyHasMember(familyId, userId)) {
                throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_FAMILY_MEMBER);
            }

            family = familyService.getFamily(familyId);
        }

        PetCategory petCategory = petCategoryService.getPetCategory(petInfo.getPetCategoryId());

        Pet newPet = Pet.builder()
                        .name(petInfo.getName())
                        .avatarUrl(petInfo.getAvatarUrl())
                        .owner(owner)
                        .family(family)
                        .petCategory(petCategory)
                        .build();

        return petRepository.save(newPet);
    }

    public List<Pet> getFamilyPets(UUID userId, UUID familyId) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!familyService.familyHasMember(familyId, userId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_FAMILY_MEMBER);
        }

        return petRepository.findAllByFamilyId(familyId);
    }

    public Pet updatePetMedicalCard(UUID userId,
                                    UUID petId,
                                    UpdateMedicalCardRequest medicalCardInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException, ConflictLocalizedException {
        Pet pet = getPet(userId, petId);

        if (Objects.nonNull(medicalCardInfo.getName())) {
            pet.setName(medicalCardInfo.getName());
        }

        if (Objects.nonNull(medicalCardInfo.getAvatarUrl())) {
            pet.setAvatarUrl(medicalCardInfo.getAvatarUrl());
        }

        if (Objects.nonNull(medicalCardInfo.getSex())) {
            pet.setSex(medicalCardInfo.getSex());
        }

        if (Objects.nonNull(medicalCardInfo.getDateOfBirth())) {
            pet.setDateOfBirth(medicalCardInfo.getDateOfBirth());
        }

        if (Objects.nonNull(medicalCardInfo.getIsSterilized())) {
            pet.setIsSterilized(medicalCardInfo.getIsSterilized());
        }

        if (Objects.nonNull(medicalCardInfo.getChipNumber())) {
            pet.setChipNumber(medicalCardInfo.getChipNumber());
        }

        if (Objects.nonNull(medicalCardInfo.getPetCategoryId())) {
            PetCategory petCategory = petCategoryService.getPetCategory(medicalCardInfo.getPetCategoryId());
            pet.setPetCategory(petCategory);
        }

        if (Objects.nonNull(medicalCardInfo.getSpeciesId())) {
            Species species = speciesService.getSpecies(medicalCardInfo.getSpeciesId());

            if (!Objects.equals(species.getPetCategory(), pet.getPetCategory())) {
                throw new ConflictLocalizedException(ConflictLocalizedError.SPECIES_DOES_NOT_BELONG_TO_PET_CATEGORY);
            }

            pet.setSpecies(species);
        }

        return petRepository.save(pet);
    }

    public void addPetToFamily(UUID userId,
                               UUID petId,
                               AddPetToFamilyRequest info) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        Family family = familyService.getUserFamily(userId, info.getFamilyId());

        Pet pet = getPet(userId, petId);
        pet.setFamily(family);

        petRepository.save(pet);
    }
}
