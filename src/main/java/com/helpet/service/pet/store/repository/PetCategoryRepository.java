package com.helpet.service.pet.store.repository;

import com.helpet.service.pet.store.model.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetCategoryRepository extends JpaRepository<PetCategory, Integer> {
    Optional<PetCategory> findPetCategoryById(Integer id);

    boolean existsByName(String name);
}
