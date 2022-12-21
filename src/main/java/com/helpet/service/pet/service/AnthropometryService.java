package com.helpet.service.pet.service;

import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.service.error.ForbiddenLocalizedError;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.store.model.Anthropometry;
import com.helpet.service.pet.store.model.Pet;
import com.helpet.service.pet.store.repository.AnthropometryRepository;
import com.helpet.service.pet.web.dto.request.CreateAnthropometryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AnthropometryService {
    private final PetService petService;

    private final AnthropometryRepository anthropometryRepository;

    @Autowired
    public AnthropometryService(PetService petService, AnthropometryRepository anthropometryRepository) {
        this.petService = petService;
        this.anthropometryRepository = anthropometryRepository;
    }

    public Page<Anthropometry> getPetAnthropometries(UUID userId,
                                                     UUID petId,
                                                     Pageable pageable) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!petService.userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return anthropometryRepository.findAllByPetId(petId, pageable);
    }

    public Anthropometry getPetAnthropometry(UUID userId,
                                             UUID petId,
                                             UUID anthropometryId) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!petService.userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return anthropometryRepository.findAnthropometryByPetIdAndId(petId, anthropometryId)
                                      .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.ANTHROPOMETRY_DOES_NOT_EXIST));
    }

    public Anthropometry createPetAnthropometry(UUID userId,
                                                UUID petId,
                                                CreateAnthropometryRequest anthropometryInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        Pet pet = petService.getPet(userId, petId);

        Anthropometry newAnthropometry = Anthropometry.builder()
                                                      .height(anthropometryInfo.getHeight())
                                                      .weight(anthropometryInfo.getWeight())
                                                      .comment(anthropometryInfo.getComment())
                                                      .pet(pet)
                                                      .build();

        return anthropometryRepository.save(newAnthropometry);
    }
}
