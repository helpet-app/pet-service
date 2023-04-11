package com.helpet.service.pet.storage.repository;

import com.helpet.service.pet.storage.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface FamilyMemberRepository extends JpaRepository<Account, UUID> {
    @Query(value = "SELECT * FROM find_all_family_members_associated_with_user(:userId)", nativeQuery = true)
    List<UUID> findAllFamilyMemberIdsAssociatedWithUser(UUID userId);

    List<Account> findAllByIdInOrderByName(Collection<UUID> ids);

    default List<Account> findAllFamilyMembersAssociatedWithUser(UUID userId) {
        List<UUID> memberIds = findAllFamilyMemberIdsAssociatedWithUser(userId);
        return findAllByIdInOrderByName(memberIds);
    }
}
