package org.studytest.savings_deposit.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.SavingsAccount;
import org.studytest.savings_deposit.repositories.SavingsAccountRepository;
import org.studytest.savings_deposit.services.SavingsAccountService;

import java.util.List;
import java.util.Optional;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {
    private final SavingsAccountRepository savingsAccountRepository;

    @Autowired
    public SavingsAccountServiceImpl(SavingsAccountRepository savingsAccountRepository) {
        this.savingsAccountRepository = savingsAccountRepository;
    }

    @Override
    public Account getSavingsAccountById(Long id) {
        Optional<Account> savingsAccountOptional = savingsAccountRepository.findById(id);
        return savingsAccountOptional.orElse(null);
    }
//TODO
    @Override
    public List<SavingsAccount> getAllSavingsAccountsByCustomerId(Long customerId) {
        return null;
    }

    @Override
    public SavingsAccount createSavingsAccount(SavingsAccount savingsAccount) {
        return null;
    }

    @Override
    public SavingsAccount updateSavingsAccount(Long id, SavingsAccount updatedSavingsAccount) {
        return null;
    }

    @Override
    public String deleteSavingsAccount(Long id) {
        return null;
    }
}
