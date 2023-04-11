package com.helpet.service.pet.storage.repository;

import com.helpet.service.pet.storage.model.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetCategoryRepository extends JpaRepository<PetCategory, Integer> {
    List<PetCategory> findAllByOrderByName();

    Optional<PetCategory> findPetCategoryById(Integer id);

    boolean existsByNameIgnoreCase(String name);
}
