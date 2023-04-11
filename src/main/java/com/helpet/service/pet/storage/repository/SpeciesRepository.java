package com.helpet.service.pet.storage.repository;

import com.helpet.service.pet.storage.model.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Integer> {
    Page<Species> findAllByPetCategoryIdAndNameContainingIgnoreCaseOrderByName(Integer petCategoryId, String name, Pageable pageable);

    boolean existsByNameIgnoreCaseAndPetCategoryId(String name, Integer petCategoryId);

    @EntityGraph(attributePaths = "petCategory")
    Optional<Species> findSpeciesById(Integer id);
}
