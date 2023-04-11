package com.helpet.service.pet.storage.repository;

import com.helpet.service.pet.storage.model.PetVaccination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetVaccinationRepository extends JpaRepository<PetVaccination, UUID> {
    Page<PetVaccination> findAllByPetIdOrderByVaccinatedOnDesc(UUID petId, Pageable pageable);

    Optional<PetVaccination> findPetVaccinationByPetIdAndId(UUID petId, UUID id);
}
