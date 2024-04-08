package org.studytest.savings_deposit.services;

import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.SavingsAccount;
import org.studytest.savings_deposit.payload.SavingsAccountDTO;

import java.util.List;
import java.util.UUID;

public interface SavingsAccountService {
    public SavingsAccount getSavingsAccountById(Long id);

    public List<SavingsAccount> getAllSavingsAccountsByCustomer(String identification_number);

    public String createSavingsAccount(String bankAccountNumber ,SavingsAccountDTO savingsAccountDTO);

    public String updateSavingsAccount(Long id);

    public String deleteSavingsAccount(Long id);

    public void updateAllSavingsAccounts();
}