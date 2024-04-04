package org.studytest.savings_deposit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.SavingsAccount;

import java.util.List;
import java.util.Optional;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {
    List<SavingsAccount> findSavingsAccountByCustomerId(Long customerId);

    boolean existsByAccountNumber(String accountNumber);
}
