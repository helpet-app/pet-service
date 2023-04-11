package com.helpet.service.pet.storage.repository;

import com.helpet.service.pet.storage.model.PetDisease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetDiseaseRepository extends JpaRepository<PetDisease, UUID> {
    Page<PetDisease> findAllByPetIdOrderByGotSickOnDesc(UUID petId, Pageable pageable);

    Optional<PetDisease> findPetDiseaseByPetIdAndId(UUID petId, UUID id);
}
