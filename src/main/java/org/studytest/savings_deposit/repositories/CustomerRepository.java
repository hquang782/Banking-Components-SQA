package org.studytest.savings_deposit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studytest.savings_deposit.models.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByIdentificationNumber (String identificationNumber);
    Optional<Customer> findCustomerByBankAccountNumber(String bankAccountNumber);
    Customer findCustomerByAccount_Id(UUID account_id);
}