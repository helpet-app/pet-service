package com.helpet.service.pet.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.dto.request.CreatePetAnthropometryRequest;
import com.helpet.service.pet.dto.request.UpdatePetAnthropometryRequest;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.storage.model.Pet;
import com.helpet.service.pet.storage.model.PetAnthropometry;
import com.helpet.service.pet.storage.repository.PetAnthropometryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PetAnthropometryService {
    private final PetService petService;

    private final PetAnthropometryRepository petAnthropometryRepository;

    @Autowired
    public PetAnthropometryService(PetService petService, PetAnthropometryRepository petAnthropometryRepository) {
        this.petService = petService;
        this.petAnthropometryRepository = petAnthropometryRepository;
    }

    public Page<PetAnthropometry> getPetAnthropometries(UUID userId,
                                                        UUID petId,
                                                        Pageable pageable) throws NotFoundLocalizedException {
        if (!petService.userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return petAnthropometryRepository.findAllByPetIdOrderByCreatedAtDesc(petId, pageable);
    }

    public PetAnthropometry getPetAnthropometry(UUID userId,
                                                UUID petId,
                                                UUID anthropometryId) throws NotFoundLocalizedException {
        if (!petService.userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return petAnthropometryRepository.findPetAnthropometryByPetIdAndId(petId, anthropometryId)
                                         .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_HAVE_THIS_ANTHROPOMETRY));
    }

    public PetAnthropometry createPetAnthropometry(UUID userId,
                                                   UUID petId,
                                                   CreatePetAnthropometryRequest anthropometryInfo) throws NotFoundLocalizedException {
        Pet pet = petService.getUserPet(userId, petId);

        PetAnthropometry newPetAnthropometry = PetAnthropometry.builder()
                                                               .height(anthropometryInfo.getHeight())
                                                               .weight(anthropometryInfo.getWeight())
                                                               .comment(anthropometryInfo.getComment())
                                                               .pet(pet)
                                                               .build();

        return petAnthropometryRepository.save(newPetAnthropometry);
    }

    public PetAnthropometry updatePetAnthropometry(UUID userId,
                                                   UUID petId,
                                                   UUID anthropometryId,
                                                   UpdatePetAnthropometryRequest anthropometryInfo) throws NotFoundLocalizedException {
        PetAnthropometry petAnthropometry = getPetAnthropometry(userId, petId, anthropometryId);

        petAnthropometry.setHeight(anthropometryInfo.getHeight());
        petAnthropometry.setWeight(anthropometryInfo.getWeight());
        petAnthropometry.setComment(anthropometryInfo.getComment());

        return petAnthropometryRepository.save(petAnthropometry);
    }

    public void deletePetAnthropometry(UUID userId, UUID petId, UUID anthropometryId) throws NotFoundLocalizedException {
        PetAnthropometry petAnthropometry = getPetAnthropometry(userId, petId, anthropometryId);

        petAnthropometryRepository.delete(petAnthropometry);
    }
}
