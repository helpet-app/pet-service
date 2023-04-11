package com.helpet.service.pet.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.dto.request.CreatePetFeatureRequest;
import com.helpet.service.pet.dto.request.UpdatePetFeatureRequest;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.storage.model.Pet;
import com.helpet.service.pet.storage.model.PetFeature;
import com.helpet.service.pet.storage.repository.PetFeatureRepository;
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

    public List<PetFeature> getPetFeatures(UUID userId, UUID petId) throws NotFoundLocalizedException {
        if (!petService.userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return petFeatureRepository.findAllByPetIdOrderByName(petId);
    }

    public PetFeature getPetFeature(UUID userId,
                                    UUID petId,
                                    UUID petFeatureId) throws NotFoundLocalizedException {
        if (!petService.userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return petFeatureRepository.findPetFeatureByPetIdAndId(petId, petFeatureId)
                                   .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_HAVE_THIS_FEATURE));
    }

    public PetFeature createPetFeature(UUID userId,
                                       UUID petId,
                                       CreatePetFeatureRequest petFeatureInfo) throws NotFoundLocalizedException {
        Pet pet = petService.getUserPet(userId, petId);

        PetFeature newPetFeature = PetFeature.builder()
                                             .name(petFeatureInfo.getName())
                                             .description(petFeatureInfo.getDescription())
                                             .pet(pet)
                                             .build();

        return petFeatureRepository.save(newPetFeature);
    }

    public PetFeature updatePetFeature(UUID userId,
                                       UUID petId,
                                       UUID featureId,
                                       UpdatePetFeatureRequest petFeatureInfo) throws NotFoundLocalizedException {
        PetFeature petFeature = getPetFeature(userId, petId, featureId);

        petFeature.setName(petFeatureInfo.getName());
        petFeature.setDescription(petFeatureInfo.getDescription());

        return petFeatureRepository.save(petFeature);
    }

    public void deletePetFeature(UUID userId, UUID petId, UUID featureId) throws NotFoundLocalizedException {
        PetFeature petFeature = getPetFeature(userId, petId, featureId);

        petFeatureRepository.delete(petFeature);
    }
}
