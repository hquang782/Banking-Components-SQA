package org.studytest.savings_deposit.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.studytest.savings_deposit.models.Customer;

import java.text.SimpleDateFormat;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SavingsAccountDTO {
    private String accountName;
    private String savingsType;
    private Date depositDate;
    private Date maturityDate;
    private String term;
    private Double depositAmount;
    private Double totalAmount;
    private Double interestRate;
    private String accountNumber;
    private String interestPaymentMethod;
    private Customer customer;
    // Format depositDate as "hh:mm:ss-dd/MM/yyyy"
    public String getDepositDate() {
        return formatDate(depositDate);
    }

    public SavingsAccountDTO setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
        return this; // Trả về this để cho phép gọi setter liên tiếp
    }

    // Format maturityDate as "hh:mm:ss-dd/MM/yyyy"
    public String getMaturityDate() {
        return formatDate(maturityDate);
    }

    public SavingsAccountDTO setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
        return this; // Trả về this để cho phép gọi setter liên tiếp
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss-dd/MM/yyyy");
        return formatter.format(date);
    }
}

