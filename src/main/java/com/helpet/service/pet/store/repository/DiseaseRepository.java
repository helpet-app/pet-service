package com.helpet.service.pet.store.repository;

import com.helpet.service.pet.store.model.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, UUID> {
    Page<Disease> findAllByPetId(UUID petId, Pageable pageable);

    Optional<Disease> findDiseaseByPetIdAndId(UUID petId, UUID id);
}
