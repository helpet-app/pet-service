package com.helpet.service.pet.store.repository;

import com.helpet.service.pet.store.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
    @Query(value = "SELECT * FROM find_all_pets_related_to_user(:userId)", nativeQuery = true)
    List<Pet> findAllPetsRelatedToUser(UUID userId);

    Optional<Pet> findPetById(UUID petId);

    List<Pet> findAllByFamilyId(UUID familyId);
}
