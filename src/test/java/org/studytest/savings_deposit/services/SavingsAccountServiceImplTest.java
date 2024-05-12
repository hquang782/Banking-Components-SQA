package org.studytest.savings_deposit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.models.InterestRate;
import org.studytest.savings_deposit.models.SavingsAccount;
import org.studytest.savings_deposit.payload.SavingsAccountDTO;
import org.studytest.savings_deposit.repositories.SavingsAccountRepository;
import org.studytest.savings_deposit.services.Impl.SavingsAccountServiceImpl;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class SavingsAccountServiceImplTest {
    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private InterestRateService interestRateService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SavingsAccountServiceImpl savingsAccountService;

    @BeforeEach
    void setUp() {
    }


    @Test
    @Transactional// không tạo mới
    @Rollback// không ảnh hưởng dữ liệu thực
    void testCreateSavingsAccountWithValidData() {
        SavingsAccountDTO savingsAccountDTO = getSavingAccountDTO();
        String result = savingsAccountService.createSavingsAccount("987654321", savingsAccountDTO);
        assertEquals("Savings account created successfully", result);
    }
    private SavingsAccountDTO getSavingAccountDTO(){
        SavingsAccountDTO savingsAccountDTO = new SavingsAccountDTO();
        savingsAccountDTO.setAccountName("mua đất");
        savingsAccountDTO.setSavingsType("Hình thức thông thường");
        savingsAccountDTO.setDepositDate(java.sql.Date.valueOf(LocalDate.parse("2024-05-12")) );
        savingsAccountDTO.setMaturityDate(java.sql.Date.valueOf(LocalDate.parse("2024-07-12")));
        savingsAccountDTO.setDepositAmount(2100000.0);
        savingsAccountDTO.setTerm("2 tháng");
        savingsAccountDTO.setStatus("active");
        savingsAccountDTO.setInterestRateValue(1.7);
        savingsAccountDTO.setInterestPaymentMethod("Lãi nhận về tài khoản");
        return savingsAccountDTO;
    }


    @Test
    public void testGetAllSavingsAccountsByCustomer_WhenCustomerExists() {
        String identificationNumber = "123456789";
        List<SavingsAccount> actualSavingsAccounts = savingsAccountService.getAllSavingsAccountsByCustomer(identificationNumber);
        assertEquals(2, actualSavingsAccounts.size());
    }

    @Test
    public void testGetAllSavingsAccountsByCustomer_WhenCustomerDoesNotExist() {
        String identificationNumber = "1234567890";
        List<SavingsAccount> actualSavingsAccounts = savingsAccountService.getAllSavingsAccountsByCustomer(identificationNumber);
        // không tìm thấy sổ tiết kiệm nào
        assertEquals(0, actualSavingsAccounts.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateSavingsAccount_MaturityDateReached_Success() {
        SavingsAccountDTO savingsAccountDTO = getSavingAccountDTO();
        InterestRate interestRate = interestRateService.getInterestRateByTerm(savingsAccountDTO.getTerm());
        Optional<Customer> customer = customerService.getCustomerByBankAccountNumber("987654321");
        SavingsAccount existingAccount = new SavingsAccount();
        existingAccount.setAccountName(savingsAccountDTO.getAccountName());
        existingAccount.setSavingsType(savingsAccountDTO.getSavingsType());
        existingAccount.setDepositDate(savingsAccountDTO.getDepositDate());
        existingAccount.setMaturityDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        existingAccount.setTerm(savingsAccountDTO.getTerm());
        existingAccount.setInterestPaymentMethod("Lãi nhập gốc");
        existingAccount.setDepositAmount(savingsAccountDTO.getDepositAmount());
        existingAccount.setTotalAmount(1000.0);
        existingAccount.setStatus(savingsAccountDTO.getStatus());
        existingAccount.setCustomer(customer.get());
        existingAccount.setInterestRate(interestRate);
        existingAccount.setInterestRateValue(interestRate.getRate());
        existingAccount.setAccountNumber(UUID.randomUUID().toString());
        existingAccount = savingsAccountRepository.save(existingAccount);
        String result = savingsAccountService.updateSavingsAccount(existingAccount.getId());
        assertEquals("Savings account updated successfully", result);
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateSavingsAccount_NotMatured() {
        String result = savingsAccountService.updateSavingsAccount(4L);
        assertEquals("Savings account not matured yet", result);
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateSavingsAccount_NotFound() {
        String result = savingsAccountService.updateSavingsAccount(-1L);
        assertEquals("Savings account not found", result);
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteSavingsAccount_Success() {
        String result = savingsAccountService.deleteSavingsAccount(4L);
        assertEquals("Savings account matured successfully.", result);
    }

    @Test
    @Rollback
    public void testDeleteSavingsAccount_NotFound() {
        String result = savingsAccountService.deleteSavingsAccount(-1L);
        assertEquals("Savings account not found", result);
    }

    @Test
    @Rollback
    public void testUpdateAllSavingsAccounts(){
        String result = savingsAccountService.updateAllSavingsAccounts();
        assertEquals("update success!", result);
    }
}
