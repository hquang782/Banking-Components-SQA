package org.studytest.savings_deposit.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studytest.savings_deposit.mappers.CustomerMapper;
import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.repositories.CustomerRepository;
import org.studytest.savings_deposit.services.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl( CustomerRepository customerRepository,CustomerMapper customerMapper) {

        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> getCustomerByIdentificationNumber(String identificationNumber) {
        return customerRepository.findCustomerByIdentificationNumber(identificationNumber);
    }

    @Override
    public Optional<Customer> getCustomerByBankAccountNumber(String bankAccountNumber) {
        return customerRepository.findCustomerByBankAccountNumber(bankAccountNumber);
    }

    @Override
    public CustomerDTO getCustomerByAccountId(UUID account_id) {
        return customerMapper.convertToDTO(customerRepository.findCustomerByAccount_Id(account_id));
    }

    @Override
    public String createCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = customerMapper.convertToEntity(customerDTO);
        System.out.println(newCustomer.getAccount().getId());
        customerRepository.save(newCustomer);
        return  "Customer created successfully!";
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public String deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customerRepository.deleteById(id);

        return "Customer with id " + id + " deleted successfully.";
    }
}
