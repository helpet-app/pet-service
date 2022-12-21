package com.helpet.service.pet.store.repository;

import com.helpet.service.pet.store.model.Vaccination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, UUID> {
    Page<Vaccination> findAllByPetId(UUID petId, Pageable pageable);

    Optional<Vaccination> findVaccinationByPetIdAndId(UUID petId, UUID id);
}
