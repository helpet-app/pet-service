package com.helpet.service.pet.store.repository;

import com.helpet.service.pet.store.model.PetFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetFeatureRepository extends JpaRepository<PetFeature, UUID> {
    List<PetFeature> findAllByPetIdOrderByName(UUID petId);

    Optional<PetFeature> findPetFeatureByPetIdAndId(UUID petId, UUID id);
}
