package org.studytest.savings_deposit.services;

import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.SavingsAccount;

import java.util.List;

public interface SavingsAccountService {
    public Account getSavingsAccountById(Long id);
    public List<SavingsAccount> getAllSavingsAccountsByCustomerId(Long customerId);
    public SavingsAccount createSavingsAccount(SavingsAccount savingsAccount);
    public SavingsAccount updateSavingsAccount(Long id, SavingsAccount updatedSavingsAccount);
    public String deleteSavingsAccount(Long id);
}
