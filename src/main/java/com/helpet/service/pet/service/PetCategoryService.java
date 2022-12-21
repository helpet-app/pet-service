package com.helpet.service.pet.service;

import com.helpet.exception.ConflictLocalizedException;
import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.pet.service.error.ConflictLocalizedError;
import com.helpet.service.pet.service.error.NotFoundLocalizedError;
import com.helpet.service.pet.store.model.PetCategory;
import com.helpet.service.pet.store.repository.PetCategoryRepository;
import com.helpet.service.pet.web.dto.request.CreatePetCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetCategoryService {
    private final PetCategoryRepository petCategoryRepository;

    @Autowired
    public PetCategoryService(PetCategoryRepository petCategoryRepository) {
        this.petCategoryRepository = petCategoryRepository;
    }

    public PetCategory getPetCategory(Integer petCategoryId) throws NotFoundLocalizedException {
        return petCategoryRepository.findPetCategoryById(petCategoryId)
                                    .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.PET_CATEGORY_DOES_NOT_EXIST));
    }

    public List<PetCategory> getPetCategories() {
        return petCategoryRepository.findAll();
    }

    public PetCategory createPetCategory(CreatePetCategoryRequest petCategoryInfo) throws ConflictLocalizedException {
        String name = petCategoryInfo.getName();

        if (petCategoryRepository.existsByName(name)) {
            throw new ConflictLocalizedException(ConflictLocalizedError.PET_CATEGORY_ALREADY_EXISTS);
        }

        PetCategory newPetCategory = PetCategory.builder()
                                                .name(name)
                                                .build();

        return petCategoryRepository.save(newPetCategory);
    }
}
