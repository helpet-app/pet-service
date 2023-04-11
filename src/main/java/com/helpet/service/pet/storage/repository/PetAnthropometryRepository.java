package com.helpet.service.pet.storage.repository;

import com.helpet.service.pet.storage.model.PetAnthropometry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetAnthropometryRepository extends JpaRepository<PetAnthropometry, UUID> {
    Page<PetAnthropometry> findAllByPetIdOrderByCreatedAtDesc(UUID petId, Pageable pageable);

    Optional<PetAnthropometry> findPetAnthropometryByPetIdAndId(UUID petId, UUID id);
}
