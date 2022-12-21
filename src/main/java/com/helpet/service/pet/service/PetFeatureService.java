package com.helpet.service.pet.service;

import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.service.error.ForbiddenLocalizedError;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.store.model.Pet;
import com.helpet.service.pet.store.model.PetFeature;
import com.helpet.service.pet.store.repository.PetFeatureRepository;
import com.helpet.service.pet.web.dto.request.CreatePetFeatureRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PetFeatureService {
    private final PetService petService;

    private final PetFeatureRepository petFeatureRepository;

    @Autowired
    public PetFeatureService(PetService petService, PetFeatureRepository petFeatureRepository) {
        this.petService = petService;
        this.petFeatureRepository = petFeatureRepository;
    }

    public List<PetFeature> getPetFeatures(UUID userId, UUID petId) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!petService.userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return petFeatureRepository.findAllByPetIdOrderByName(petId);
    }

    public PetFeature getPetFeature(UUID userId,
                                    UUID petId,
                                    UUID petFeatureId) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!petService.userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return petFeatureRepository.findPetFeatureByPetIdAndId(petId, petFeatureId)
                                   .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_FEATURE_DOES_NOT_EXIST));
    }

    public PetFeature createPetFeature(UUID userId,
                                       UUID petId,
                                       CreatePetFeatureRequest petFeatureInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        Pet pet = petService.getPet(userId, petId);

        PetFeature newPetFeature = PetFeature.builder()
                                             .name(petFeatureInfo.getName())
                                             .description(petFeatureInfo.getDescription())
                                             .pet(pet)
                                             .build();

        return petFeatureRepository.save(newPetFeature);
    }
}
