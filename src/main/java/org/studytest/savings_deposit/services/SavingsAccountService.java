package org.studytest.savings_deposit.services;

import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.SavingsAccount;
import org.studytest.savings_deposit.payload.SavingsAccountDTO;

import java.util.List;

public interface SavingsAccountService {
    public SavingsAccount getSavingsAccountById(Long id);

    public List<SavingsAccount> getAllSavingsAccountsByCustomerId(Long customerId);

    public String createSavingsAccount(Long customerId ,SavingsAccountDTO savingsAccountDTO);

    public String updateSavingsAccount(Long id);

    public String deleteSavingsAccount(Long id);
}