package org.studytest.savings_deposit.services;

import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.payload.AccountDTO;
import org.studytest.savings_deposit.payload.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Optional<Customer> getCustomerById(Long id);
    String createCustomer(CustomerDTO customerDTO);
    Optional<Customer> getCustomerByIdentificationNumber(String identificationNumber);
    Optional<Customer> getCustomerByBankAccountNumber(String bankAccountNumber);
    CustomerDTO getCustomerByAccountId(UUID account_id);
    String updateAccount(UUID account_id, AccountDTO accountDTO);
}
