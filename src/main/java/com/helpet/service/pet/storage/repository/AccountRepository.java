package com.helpet.service.pet.storage.repository;

import com.helpet.service.pet.storage.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAccountById(UUID accountId);

    Optional<Account> findAccountByUsernameIgnoreCase(String username);
}
