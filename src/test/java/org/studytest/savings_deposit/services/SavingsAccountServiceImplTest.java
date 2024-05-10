package org.studytest.savings_deposit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.studytest.savings_deposit.payload.SavingsAccountDTO;
import org.studytest.savings_deposit.repositories.SavingsAccountRepository;
import org.studytest.savings_deposit.services.Impl.SavingsAccountServiceImpl;

import java.time.LocalDate;

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
        savingsAccountDTO.setAccountName("mua nhà");
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
}
