package org.studytest.savings_deposit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studytest.savings_deposit.models.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByIdentificationNumber (String identificationNumber);
    Optional<Customer> findCustomerByBankAccountNumber(String bankAccountNumber);
}