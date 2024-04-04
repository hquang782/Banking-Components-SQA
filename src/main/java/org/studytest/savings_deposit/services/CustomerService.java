package org.studytest.savings_deposit.services;

import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.payload.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    Optional<Customer> getCustomerById(Long id);
    String createCustomer(CustomerDTO customerDTO);
    Customer updateCustomer(Long id,Customer customer);
    String deleteCustomer(Long id);
    Optional<Customer> getCustomerByIdentificationNumber(String identificationNumber);
    Optional<Customer> getCustomerByBankAccountNumber(String bankAccountNumber);
}
