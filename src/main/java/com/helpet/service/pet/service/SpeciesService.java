package com.helpet.service.pet.service;

import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.service.error.ConflictLocalizedError;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.storage.model.PetCategory;
import com.helpet.service.pet.storage.model.Species;
import com.helpet.service.pet.storage.repository.SpeciesRepository;
import com.helpet.service.pet.dto.request.CreateSpeciesRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpeciesService {
    private final PetCategoryService petCategoryService;

    private final SpeciesRepository speciesRepository;

    public SpeciesService(PetCategoryService petCategoryService, SpeciesRepository speciesRepository) {
        this.petCategoryService = petCategoryService;
        this.speciesRepository = speciesRepository;
    }

    public Species getSpecies(Integer id) throws NotFoundLocalizedException {
        return speciesRepository.findSpeciesById(id)
                                .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.SPECIES_DOES_NOT_EXIST));
    }

    public Page<Species> getCategorySpecies(Integer petCategoryId, String name, Pageable pageable) {
        return speciesRepository.findAllByPetCategoryIdAndNameContainingIgnoreCaseOrderByName(petCategoryId, name, pageable);
    }

    public Species createSpecies(Integer petCategoryId,
                                 CreateSpeciesRequest speciesInfo) throws NotFoundLocalizedException, ConflictLocalizedException {
        String name = speciesInfo.getName();

        if (speciesRepository.existsByNameIgnoreCaseAndPetCategoryId(name, petCategoryId)) {
            throw new ConflictLocalizedException(ConflictLocalizedError.SPECIES_ALREADY_EXISTS);
        }

        PetCategory petCategory = petCategoryService.getPetCategory(petCategoryId);

        Species newSpecies = Species.builder()
                                    .name(name)
                                    .petCategory(petCategory)
                                    .build();

        return speciesRepository.save(newSpecies);
    }
}
