package org.studytest.savings_deposit.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.services.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    // Lấy danh sách tất cả các khách hàng
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Lấy thông tin của một khách hàng dựa trên ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.getCustomerById(id);
        return customerOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Cập nhật thông tin của một khách hàng
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Optional<Customer> customerOptional = customerService.getCustomerById(id);
        if (customerOptional.isPresent()) {
            Customer existingCustomer = customerOptional.get();
            // Cập nhật thông tin từ CustomerDTO vào existingCustomer
            existingCustomer.setFullName(customerDTO.getFullName());
            existingCustomer.setAge(customerDTO.getAge());
            existingCustomer.setGender(customerDTO.getGender());
            existingCustomer.setAddress(customerDTO.getAddress());
            existingCustomer.setEmail(customerDTO.getEmail());
            existingCustomer.setIdentificationNumber(customerDTO.getIdentificationNumber());
            existingCustomer.setBankAccountNumber(customerDTO.getBankAccountNumber());
            existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());

            // Gọi đến customerService để cập nhật existingCustomer
            Customer savedCustomer = customerService.updateCustomer(id,existingCustomer);

            // Trả về phản hồi HTTP 200 OK với thông báo thành công
            return ResponseEntity.ok("Customer updated successfully with id: " + savedCustomer.getId());
        } else {
            // Trả về phản hồi HTTP 404 Not Found nếu không tìm thấy khách hàng
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa một khách hàng dựa trên ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.getCustomerById(id);

        if (customerOptional.isPresent()) {
            return ResponseEntity.ok(customerService.deleteCustomer(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
