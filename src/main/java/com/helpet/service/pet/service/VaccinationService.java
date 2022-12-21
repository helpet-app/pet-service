package com.helpet.service.pet.service;

import com.helpet.exception.ForbiddenLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.service.error.ForbiddenLocalizedError;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.store.model.Pet;
import com.helpet.service.pet.store.model.Vaccination;
import com.helpet.service.pet.store.repository.VaccinationRepository;
import com.helpet.service.pet.web.dto.request.CreateVaccinationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VaccinationService {
    private final PetService petService;

    private final VaccinationRepository vaccinationRepository;

    @Autowired
    public VaccinationService(PetService petService, VaccinationRepository vaccinationRepository) {
        this.petService = petService;
        this.vaccinationRepository = vaccinationRepository;
    }

    public Page<Vaccination> getPetVaccinations(UUID userId,
                                                UUID petId,
                                                Pageable pageable) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!petService.userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return vaccinationRepository.findAllByPetId(petId, pageable);
    }

    public Vaccination getPetVaccination(UUID userId,
                                         UUID petId,
                                         UUID vaccinationId) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        if (!petService.userIsRelatedToPet(userId, petId)) {
            throw new ForbiddenLocalizedException(ForbiddenLocalizedError.USER_IS_NOT_RELATED_TO_PET);
        }

        return vaccinationRepository.findVaccinationByPetIdAndId(petId, vaccinationId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.VACCINATION_DOES_NOT_EXIST));
    }

    public Vaccination createPetVaccination(UUID userId,
                                            UUID petId,
                                            CreateVaccinationRequest vaccinationInfo) throws NotFoundLocalizedException, ForbiddenLocalizedException {
        Pet pet = petService.getPet(userId, petId);

        Vaccination newVaccination = Vaccination.builder()
                                                .vaccinationName(vaccinationInfo.getVaccinationName())
                                                .comment(vaccinationInfo.getComment())
                                                .vaccinatedOn(vaccinationInfo.getVaccinatedOn())
                                                .pet(pet)
                                                .build();

        return vaccinationRepository.save(newVaccination);
    }
}
