package org.studytest.savings_deposit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "savings_accounts")
public class SavingsAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "savings_type")
    private String savingsType;
//    ví dụ: tiết kiệm thông thường, tiết kiệm trực tuyến, vv.

    @Column(name = "deposit_date")
    @Temporal(TemporalType.DATE)
    private Date depositDate;

    @Column(name = "maturity_date")
    @Temporal(TemporalType.DATE)
    private Date maturityDate;

    @Column(name = "term")
    private String term;

    @Column(name = "deposit_amount")
    private Double depositAmount;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name = "interest_payment_method")
    private String interestPaymentMethod;

    @OneToOne
    @JoinColumn(name = "interestrate_id", referencedColumnName = "id")
    private InterestRate interestRate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
