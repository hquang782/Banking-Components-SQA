package org.studytest.savings_deposit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studytest.savings_deposit.models.Account;

public interface SavingsAccountRepository extends JpaRepository<Account, Long> {

}
