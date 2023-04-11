package com.helpet.service.pet.storage.repository;

import com.helpet.service.pet.storage.model.Family;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {
    @EntityGraph(attributePaths = "createdBy")
    List<Family> findAllByMembersIdOrderByName(UUID memberId);

    @EntityGraph(attributePaths = "createdBy")
    Optional<Family> findFamilyByMembersIdAndId(UUID memberId, UUID familyId);

    @EntityGraph(attributePaths = {
            "createdBy",
            "members"
    })
    Optional<Family> findFamilyWithMembersByMembersIdAndId(UUID memberId, UUID familyId);

    @EntityGraph(attributePaths = {
            "createdBy",
            "pets.petCategory",
            "pets.species"
    })
    Optional<Family> findFamilyWithPetsByMembersIdAndId(UUID memberId, UUID familyId);
}