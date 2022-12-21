package com.helpet.service.pet.service;

import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.service.error.ForbiddenLocalizedError;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.store.model.Disease;
import com.helpet.service.pet.store.model.Pet;
import com.helpet.service.pet.store.repository.DiseaseRepository;
import com.helpet.service.pet.web.dto.request.CreateDiseaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiseaseService {
    private final PetService petService;

    private final DiseaseRepository diseaseRepository;

    @Autowired
    public DiseaseService(PetService petService, DiseaseRepository diseaseRepository) {
        this.petService = petService;
        this.diseaseRepository = diseaseRepository;
    }

    public Page<Disease> getPetDiseases(UUID userId,
                                        UUID petId,
                                        Pageable pageable) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!petService.userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return diseaseRepository.findAllByPetId(petId, pageable);
    }

    public Disease getPetDisease(UUID userId,
                                 UUID petId,
                                 UUID diseaseId) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!petService.userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return diseaseRepository.findDiseaseByPetIdAndId(petId, diseaseId)
                                .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.DISEASE_DOES_NOT_EXIST));
    }

    public Disease createPetDisease(UUID userId,
                                    UUID petId,
                                    CreateDiseaseRequest diseaseInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        Pet pet = petService.getPet(userId, petId);

        Disease newDisease = Disease.builder()
                                    .diseaseName(diseaseInfo.getDiseaseName())
                                    .comment(diseaseInfo.getComment())
                                    .gotSickOn(diseaseInfo.getGotSickOn())
                                    .recoveredOn(diseaseInfo.getRecoveredOn())
                                    .pet(pet)
                                    .build();

        return diseaseRepository.save(newDisease);
    }
}
