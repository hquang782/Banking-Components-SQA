package org.studytest.savings_deposit.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.studytest.savings_deposit.mappers.CustomerMapper;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.repositories.CustomerRepository;
import org.studytest.savings_deposit.services.Impl.CustomerServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testGetCustomerByAccountId() {
        // Tạo một tài khoản giả định
        Account mockAccount = new Account();
        mockAccount.setId(UUID.randomUUID());
        mockAccount.setUsername("test");
        mockAccount.setPassword("test");

        // Tạo một Customer giả định
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFullName("John Doe");
        customer.setAccount(mockAccount);
        // Tạo một CustomerDTO giả định
        CustomerDTO expectedCustomerDTO = new CustomerDTO();
        expectedCustomerDTO.setFullName("John Doe");

        // Mock phương thức findCustomerByAccount_Id của customerRepository để trả về một Optional chứa customer giả định
        Mockito.when(customerRepository.findCustomerByAccount_Id(any(UUID.class)))
                .thenReturn(customer);

        // Mock phương thức convertToDTO của customerMapper để trả về expectedCustomerDTO
        Mockito.when(customerMapper.convertToDTO(customer))
                .thenReturn(expectedCustomerDTO);

        // Gọi phương thức getCustomerByAccountId với accountId
        CustomerDTO result = customerService.getCustomerByAccountId(mockAccount.getId());

        // Kiểm tra xem kết quả trả về có đúng hay không
        assertEquals(expectedCustomerDTO, result);
    }
}