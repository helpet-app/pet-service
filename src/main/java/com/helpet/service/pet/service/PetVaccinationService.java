package com.helpet.service.pet.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.dto.request.CreatePetVaccinationRequest;
import com.helpet.service.pet.dto.request.UpdatePetVaccinationRequest;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.storage.model.Pet;
import com.helpet.service.pet.storage.model.PetVaccination;
import com.helpet.service.pet.storage.repository.PetVaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PetVaccinationService {
    private final PetService petService;

    private final PetVaccinationRepository petVaccinationRepository;

    @Autowired
    public PetVaccinationService(PetService petService, PetVaccinationRepository petVaccinationRepository) {
        this.petService = petService;
        this.petVaccinationRepository = petVaccinationRepository;
    }

    public Page<PetVaccination> getPetVaccinations(UUID userId,
                                                   UUID petId,
                                                   Pageable pageable) throws NotFoundLocalizedException {
        if (!petService.userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return petVaccinationRepository.findAllByPetIdOrderByVaccinatedOnDesc(petId, pageable);
    }

    public PetVaccination getPetVaccination(UUID userId,
                                            UUID petId,
                                            UUID vaccinationId) throws NotFoundLocalizedException {
        if (!petService.userIsAssociatedWithPet(userId, petId)) {
            throw new NotFoundLocalizedException(NotFoundLocalizedError.USER_DOES_NOT_HAVE_THIS_PET);
        }

        return petVaccinationRepository.findPetVaccinationByPetIdAndId(petId, vaccinationId)
                                       .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_DOES_NOT_HAVE_THIS_VACCINATION));
    }

    public PetVaccination createPetVaccination(UUID userId,
                                               UUID petId,
                                               CreatePetVaccinationRequest vaccinationInfo) throws NotFoundLocalizedException {
        Pet pet = petService.getUserPet(userId, petId);

        PetVaccination newPetVaccination = PetVaccination.builder()
                                                         .vaccinationName(vaccinationInfo.getVaccinationName())
                                                         .comment(vaccinationInfo.getComment())
                                                         .vaccinatedOn(vaccinationInfo.getVaccinatedOn())
                                                         .pet(pet)
                                                         .build();

        return petVaccinationRepository.save(newPetVaccination);
    }

    public PetVaccination updatePetVaccination(UUID userId,
                                               UUID petId,
                                               UUID vaccinationId,
                                               UpdatePetVaccinationRequest vaccinationInfo) throws NotFoundLocalizedException {
        PetVaccination petVaccination = getPetVaccination(userId, petId, vaccinationId);

        petVaccination.setVaccinationName(vaccinationInfo.getVaccinationName());
        petVaccination.setComment(vaccinationInfo.getComment());
        petVaccination.setVaccinatedOn(vaccinationInfo.getVaccinatedOn());

        return petVaccinationRepository.save(petVaccination);
    }

    public void deletePetVaccination(UUID userId, UUID petId, UUID vaccinationId) throws NotFoundLocalizedException {
        PetVaccination petVaccination = getPetVaccination(userId, petId, vaccinationId);

        petVaccinationRepository.delete(petVaccination);
    }
}
