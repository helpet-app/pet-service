package com.helpet.service.pet.service;

import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.dto.request.CreatePetRequest;
import com.helpet.service.pet.dto.request.UpdateMedicalCardRequest;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.storage.model.Account;
import com.helpet.service.pet.storage.model.Pet;
import com.helpet.service.pet.storage.model.PetCategory;
import com.helpet.service.pet.storage.model.Species;
import com.helpet.service.pet.storage.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PetService {
    private final AccountService accountService;

    private final PetCategoryService petCategoryService;

    private final SpeciesService speciesService;

    private final PetRepository petRepository;

    @Autowired
    public PetService(AccountService accountService,
                      PetCategoryService petCategoryService,
                      SpeciesService speciesService,
                      PetRepository petRepository) {
        this.accountService = accountService;
        this.petCategoryService = petCategoryService;
        this.speciesService = speciesService;
        this.petRepository = petRepository;
    }

    public boolean userIsAssociatedWithPet(UUID userId, UUID petId) throws NotFoundLocalizedException {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        if (!petRepository.existsById(petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST);
        }

        return petRepository.petIsAssociatedWithUser(petId, userId);
    }

    public List<Pet> getUserPets(UUID userId) {
        if (!accountService.accountExists(userId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST);
        }

        return petRepository.findAllPetsAssociatedWithUser(userId);
    }

    public Pet getUserPet(UUID userId, UUID petId) throws NotFoundLocalizedException {
        if (!userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return petRepository.findPetById(petId)
                            .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST));
    }

    public Pet getDetailedUserPet(UUID userId, UUID petId) throws NotFoundLocalizedException {
        if (!userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return petRepository.findDetailedPetById(petId)
                            .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_EXIST));
    }

    public Pet createPet(UUID userId, CreatePetRequest petInfo) throws NotFoundLocalizedException {
        Account createdBy = accountService.getAccount(userId);

        Integer petCategoryId = petInfo.getPetCategoryId();
        PetCategory petCategory = Objects.nonNull(petCategoryId) ? petCategoryService.getPetCategory(petCategoryId) : null;

        Pet newPet = Pet.builder()
                        .name(petInfo.getName())
                        .petCategory(petCategory)
                        .createdBy(createdBy)
                        .build();

        return petRepository.save(newPet);
    }

    public Pet updatePetMedicalCard(UUID userId,
                                    UUID petId,
                                    UpdateMedicalCardRequest medicalCardInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException, ConflictLocalizedException {
        Pet pet = getUserPet(userId, petId);

        pet.setName(medicalCardInfo.getName());
        pet.setGender(medicalCardInfo.getGender());
        pet.setDateOfBirth(medicalCardInfo.getDateOfBirth());
        pet.setIsSpayedOrNeutered(medicalCardInfo.getIsSpayedOrNeutered());
        pet.setChipNumber(medicalCardInfo.getChipNumber());

        Integer speciesId = medicalCardInfo.getSpeciesId();
        if (Objects.nonNull(speciesId)) {
            Species species = speciesService.getSpecies(speciesId);
            pet.setPetCategory(species.getPetCategory());
            pet.setSpecies(species);
        } else {
            Integer petCategoryId = medicalCardInfo.getPetCategoryId();
            PetCategory petCategory = Objects.nonNull(petCategoryId) ? petCategoryService.getPetCategory(petCategoryId) : null;
            pet.setPetCategory(petCategory);
            pet.setSpecies(null);
        }

        return petRepository.save(pet);
    }

    public void deleteUserPet(UUID userId, UUID petId) {
        Pet pet = getUserPet(userId, petId);

        petRepository.delete(pet);
    }

    public void savePet(Pet pet) {
        petRepository.save(pet);
    }
}
