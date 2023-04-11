package com.helpet.service.pet.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.dto.request.CreatePetDiseaseRequest;
import com.helpet.service.pet.dto.request.UpdatePetDiseaseRequest;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.storage.model.PetDisease;
import com.helpet.service.pet.storage.model.Pet;
import com.helpet.service.pet.storage.repository.PetDiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PetDiseaseService {
    private final PetService petService;

    private final PetDiseaseRepository petDiseaseRepository;

    @Autowired
    public PetDiseaseService(PetService petService, PetDiseaseRepository petDiseaseRepository) {
        this.petService = petService;
        this.petDiseaseRepository = petDiseaseRepository;
    }

    public Page<PetDisease> getPetDiseases(UUID userId,
                                           UUID petId,
                                           Pageable pageable) throws NotFoundLocalizedException {
        if (!petService.userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return petDiseaseRepository.findAllByPetIdOrderByGotSickOnDesc(petId, pageable);
    }

    public PetDisease getPetDisease(UUID userId,
                                    UUID petId,
                                    UUID diseaseId) throws NotFoundLocalizedException {
        if (!petService.userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return petDiseaseRepository.findPetDiseaseByPetIdAndId(petId, diseaseId)
                                   .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_HAVE_THIS_DISEASE));
    }

    public PetDisease createPetDisease(UUID userId,
                                       UUID petId,
                                       CreatePetDiseaseRequest diseaseInfo) throws NotFoundLocalizedException {
        Pet pet = petService.getUserPet(userId, petId);

        PetDisease newPetDisease = PetDisease.builder()
                                             .diseaseName(diseaseInfo.getDiseaseName())
                                             .comment(diseaseInfo.getComment())
                                             .gotSickOn(diseaseInfo.getGotSickOn())
                                             .recoveredOn(diseaseInfo.getRecoveredOn())
                                             .pet(pet)
                                             .build();

        return petDiseaseRepository.save(newPetDisease);
    }

    public PetDisease updatePetDisease(UUID userId,
                                       UUID petId,
                                       UUID diseaseId,
                                       UpdatePetDiseaseRequest diseaseInfo) throws NotFoundLocalizedException {
        PetDisease petDisease = getPetDisease(userId, petId, diseaseId);

        petDisease.setDiseaseName(diseaseInfo.getDiseaseName());
        petDisease.setComment(diseaseInfo.getComment());
        petDisease.setGotSickOn(diseaseInfo.getGotSickOn());
        petDisease.setRecoveredOn(diseaseInfo.getRecoveredOn());

        return petDiseaseRepository.save(petDisease);
    }

    public void deletePetDisease(UUID userId, UUID petId, UUID diseaseId) throws NotFoundLocalizedException {
        PetDisease petDisease = getPetDisease(userId, petId, diseaseId);

        petDiseaseRepository.delete(petDisease);
    }
}
