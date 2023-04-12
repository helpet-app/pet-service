package com.helpet.service.pet.storage.repository;

import com.helpet.service.pet.storage.model.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
    Optional<Pet> findPetById(UUID petId);

    @EntityGraph(attributePaths = {
            "petCategory",
            "species",
            "family"
    })
    Optional<Pet> findDetailedPetById(UUID petId);

    @Query(value = "SELECT * FROM find_all_pets_associated_with_user(:userId)", nativeQuery = true)
    List<UUID> findAllPetIdsAssociatedWithUser(UUID userId);

    @EntityGraph(attributePaths = {
            "petCategory",
            "species",
            "family"
    })
    List<Pet> findAllByIdInOrderByName(Collection<UUID> ids);

    default List<Pet> findAllPetsAssociatedWithUser(UUID userId) {
        List<UUID> petIds = findAllPetIdsAssociatedWithUser(userId);
        return findAllByIdInOrderByName(petIds);
    }

    @Query(value = "SELECT * FROM pet_is_associated_with_user(:petId, :userId)", nativeQuery = true)
    boolean petIsAssociatedWithUser(UUID petId, UUID userId);
}
