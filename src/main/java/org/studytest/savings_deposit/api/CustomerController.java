package org.studytest.savings_deposit.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.payload.AccountDTO;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.services.CustomerService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    // Lấy thông tin của một khách hàng dựa trên ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.getCustomerById(id);
        return customerOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //    cập nhật customer để lưu vào localstorage
    @GetMapping("/updateCustomer/{accountId}")
    public ResponseEntity<CustomerDTO> getNewCustomer(@PathVariable UUID accountId){
        CustomerDTO response = customerService.getCustomerByAccountId(accountId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            return ResponseEntity.ok(response);
        }
    }


    @PutMapping("/updateBalance/{accountId}")
    public ResponseEntity<String> updateBalance(@PathVariable UUID accountId, @RequestBody AccountDTO accountDTO)  {
        System.out.println(accountDTO);
        String result = customerService.updateAccount(accountId, accountDTO);
        return ResponseEntity.ok(result);
    }
}
