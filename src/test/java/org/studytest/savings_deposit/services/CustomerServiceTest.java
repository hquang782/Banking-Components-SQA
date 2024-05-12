package org.studytest.savings_deposit.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.studytest.savings_deposit.mappers.CustomerMapper;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.payload.AccountDTO;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.repositories.AccountRepository;
import org.studytest.savings_deposit.repositories.CustomerRepository;
import org.studytest.savings_deposit.services.Impl.CustomerServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;
    @Test
    void testGetCustomerByAccountId() {
        Account mockAccount = new Account();
        mockAccount.setId(UUID.randomUUID());
        mockAccount.setUsername("test");
        mockAccount.setPassword("test");
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFullName("John Doe");
        customer.setAccount(mockAccount);
        CustomerDTO expectedCustomerDTO = new CustomerDTO();
        expectedCustomerDTO.setFullName("John Doe");
        when(customerRepository.findCustomerByAccount_Id(any(UUID.class)))
                .thenReturn(customer);
        when(customerMapper.convertToDTO(customer))
                .thenReturn(expectedCustomerDTO);
        CustomerDTO result = customerService.getCustomerByAccountId(mockAccount.getId());
        assertEquals(expectedCustomerDTO, result);
    }

    @Test
    @Rollback
    public void testUpdateAccount_Success() {
        UUID accountId = UUID.randomUUID();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(2000.0);
        Account existingAccount = new Account();
        existingAccount.setId(accountId);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(existingAccount)).thenReturn(existingAccount);
        String result = customerService.updateAccount(accountId, accountDTO);
        assertEquals("Cập nhật tài khoản thành công!", result);
        assertEquals(accountDTO.getBalance(), existingAccount.getBalance());
        verify(accountRepository, times(1)).save(existingAccount);
    }

    @Test
    @Rollback
    public void testUpdateAccount_NotFound() {
        UUID accountId = UUID.randomUUID();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(2000.0);
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
        String result = customerService.updateAccount(accountId, accountDTO);
        assertEquals("Không tìm thấy tài khoản để cập nhật!", result);
        verify(accountRepository, never()).save(any());
    }
}