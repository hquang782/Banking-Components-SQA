package org.studytest.savings_deposit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studytest.savings_deposit.models.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByUsername(String username);
    Optional<Account> findAccountById(UUID account_Id);

    Boolean existsByUsername(String username);
}
