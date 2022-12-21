package com.helpet.service.pet.store.repository;

import com.helpet.service.pet.store.model.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Integer> {
    Page<Species> findAllByPetCategoryIdAndNameContainingIgnoreCase(Integer petCategoryId, String name, Pageable pageable);

    boolean existsByNameAndPetCategoryId(String name, Integer petCategoryId);

    Optional<Species> findSpeciesById(Integer id);
}
