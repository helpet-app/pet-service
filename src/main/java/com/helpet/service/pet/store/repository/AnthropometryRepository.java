package com.helpet.service.pet.store.repository;

import com.helpet.service.pet.store.model.Anthropometry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnthropometryRepository extends JpaRepository<Anthropometry, UUID> {
    Page<Anthropometry> findAllByPetId(UUID petId, Pageable pageable);

    Optional<Anthropometry> findAnthropometryByPetIdAndId(UUID petId, UUID id);
}
