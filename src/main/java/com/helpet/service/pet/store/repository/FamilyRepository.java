package com.helpet.service.pet.store.repository;

import com.helpet.service.pet.store.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {
    Optional<Family> findFamilyById(UUID id);

    boolean existsByIdAndMembersId(UUID familyId, UUID memberId);

    List<Family> findAllByMembersId(UUID memberId);
}