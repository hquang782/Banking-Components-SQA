package org.studytest.savings_deposit.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.studytest.savings_deposit.models.Account;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String fullName;
    private Integer age;
    private String gender;
    private String dob;
    private String address;
    private String email;
    private String phoneNumber;
    private String identificationNumber;
    private String bankAccountNumber;
    private Account account;
}