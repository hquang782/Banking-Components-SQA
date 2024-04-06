package org.studytest.savings_deposit.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.studytest.savings_deposit.models.SavingsAccount;
import org.studytest.savings_deposit.payload.SavingsAccountDTO;
import org.studytest.savings_deposit.services.SavingsAccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/savings-accounts")
public class SavingsAccountController {

    @Autowired
    private SavingsAccountService savingsAccountService;



    @GetMapping("/{id}")
    public ResponseEntity<SavingsAccount> getSavingsAccountById(@PathVariable Long id) {
        // Gọi service để lấy tài khoản tiết kiệm từ id
        SavingsAccount savingsAccount = savingsAccountService.getSavingsAccountById(id);

        if (savingsAccount != null) {
            return ResponseEntity.ok(savingsAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/list/{customerId}")
    public ResponseEntity<List<SavingsAccount>> getAllSavingsAccounts(@PathVariable Long customerId) {
        // Gọi service để lấy tất cả các tài khoản tiết kiệm cho customerId
        List<SavingsAccount> savingsAccounts = savingsAccountService.getAllSavingsAccountsByCustomerId(customerId);

        return ResponseEntity.ok(savingsAccounts);
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<String> createSavingsAccount(@PathVariable Long customerId,@RequestBody SavingsAccountDTO savingsAccountDTO) {

        // Gọi service để thêm tài khoản tiết kiệm mới
        String message = savingsAccountService.createSavingsAccount(customerId,savingsAccountDTO);

        return ResponseEntity.ok(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSavingsAccount(@PathVariable Long id) {
        // Gọi service để cập nhật thông tin của tài khoản tiết kiệm
        String message = savingsAccountService.updateSavingsAccount(id);

        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSavingsAccount(@PathVariable Long id) {
        // Gọi service để xóa tài khoản tiết kiệm
        String message = savingsAccountService.deleteSavingsAccount(id);
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
