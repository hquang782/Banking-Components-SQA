package org.studytest.savings_deposit.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.models.InterestRate;
import org.studytest.savings_deposit.models.SavingsAccount;
import org.studytest.savings_deposit.payload.SavingsAccountDTO;
import org.studytest.savings_deposit.repositories.SavingsAccountRepository;
import org.studytest.savings_deposit.services.CustomerService;
import org.studytest.savings_deposit.services.InterestRateService;
import org.studytest.savings_deposit.services.SavingsAccountService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {
    private final SavingsAccountRepository savingsAccountRepository;

    private final InterestRateService interestRateService;

    private final CustomerService customerService;


    @Autowired
    public SavingsAccountServiceImpl(SavingsAccountRepository savingsAccountRepository, CustomerService customerService, InterestRateService interestRateService) {
        this.savingsAccountRepository = savingsAccountRepository;
        this.interestRateService = interestRateService;
        this.customerService = customerService;
    }

    @Override
    public SavingsAccount getSavingsAccountById(Long id) {
        Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(id);
        return savingsAccountOptional.orElse(null);
    }

    public List<SavingsAccount> getAllSavingsAccountsByCustomerId(Long customerId) {
        return savingsAccountRepository.findActiveSavingsAccountsByCustomerIdAndStatus(customerId, "active");
    }

    @Override
    public List<SavingsAccount> getAllSavingsAccountsByCustomer(String identification_number) {
        Optional<Customer> customer = customerService.getCustomerByIdentificationNumber(identification_number);
        if (customer.isPresent()) {
            return getAllSavingsAccountsByCustomerId(customer.get().getId());
        } else {
            return Collections.emptyList();
        }
    }


    @Override
    public String createSavingsAccount(String bankAccountNumber, SavingsAccountDTO savingsAccountDTO) {

        InterestRate interestRate = interestRateService.getInterestRateByTerm(savingsAccountDTO.getTerm());
        Optional<Customer> customer = customerService.getCustomerByBankAccountNumber(bankAccountNumber);
        if (interestRate != null&& customer.isPresent()) {
            // Sinh ra một số tài khoản mới và duy nhất
            String accountNumber = UUID.randomUUID().toString();

            // Kiểm tra xem số tài khoản đã tồn tại chưa
            while (savingsAccountRepository.existsByAccountNumber(accountNumber)) {
                accountNumber = UUID.randomUUID().toString();
            }
            SavingsAccount savingsAccount = new SavingsAccount();
            savingsAccount.setAccountName(savingsAccountDTO.getAccountName());
            savingsAccount.setSavingsType(savingsAccountDTO.getSavingsType());
            savingsAccount.setDepositDate(savingsAccountDTO.getDepositDate());
            savingsAccount.setMaturityDate(savingsAccountDTO.getMaturityDate());
            savingsAccount.setTerm(savingsAccountDTO.getTerm());
            savingsAccount.setDepositAmount(savingsAccountDTO.getDepositAmount());
            long numOfDay = daysBetween(savingsAccountDTO.getDepositDate(),savingsAccountDTO.getMaturityDate());
            savingsAccount.setTotalAmount(savingsAccountDTO.getDepositAmount() + (savingsAccountDTO.getDepositAmount() * (savingsAccountDTO.getInterestRateValue()/100)*(numOfDay/365)));
            savingsAccount.setInterestRateValue(interestRate.getRate());
            savingsAccount.setStatus(savingsAccountDTO.getStatus());
            savingsAccount.setAccountNumber(accountNumber);
            savingsAccount.setInterestPaymentMethod(savingsAccountDTO.getInterestPaymentMethod());
            savingsAccount.setInterestRate(interestRate);
            savingsAccount.setCustomer(customer.get());

            savingsAccountRepository.save(savingsAccount);

            return "Savings account created successfully";
        } else {
            System.out.println("InterestRate không tồn tại");
            return "Savings account created failed !";
        }

    }

    // cập nhật lãi sau mỗi chu kì
    @Override
    public String updateSavingsAccount(Long id) {
        Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(id);
        if (savingsAccountOptional.isPresent()) {
            SavingsAccount existingAccount = savingsAccountOptional.get();
            LocalDate currentDate = LocalDate.now();
            long numOfDay = daysBetween(existingAccount.getDepositDate(),existingAccount.getMaturityDate());

            // So sánh ngày hiện tại với ngày đáo hạn
            if (currentDate.isEqual(convertToLocalDate(existingAccount.getMaturityDate()))) {
                // Cập nhật thông tin cho sổ tiết kiệm
                existingAccount.setDepositDate(convertToDate(currentDate));

                // Tính ngày đáo hạn mới
                LocalDate newMaturityDate = currentDate.plusMonths(getTermInMonths(existingAccount.getTerm()));
                existingAccount.setMaturityDate(convertToDate(newMaturityDate));

                if(existingAccount.getInterestPaymentMethod().equals("Lãi nhập gốc")){
                    // Cập nhật depositAmount
                    existingAccount.setDepositAmount(existingAccount.getTotalAmount());
                }
                else{
                    //chuyển tiền lãi về tài khoản nguồn
                    Customer customer = existingAccount.getCustomer();
                    Account account = customer.getAccount();
                    Double money = account.getBalance();
                    account.setBalance(money+ existingAccount.getTotalAmount()- existingAccount.getDepositAmount());
                    customer.setAccount(account);
                    existingAccount.setCustomer(customer);
                }

                // Lấy lãi suất mới theo term
                InterestRate interestRate = interestRateService.getInterestRateByTerm(existingAccount.getTerm());
                if (interestRate != null) {
                    // Cập nhật lãi suất mới và tính toán totalAmount mới
                    existingAccount.setInterestRateValue(interestRate.getRate());
                    double totalAmount = existingAccount.getDepositAmount() +
                            (existingAccount.getDepositAmount() * (existingAccount.getInterestRateValue()/100)*((double) numOfDay /365));
                    existingAccount.setTotalAmount(totalAmount);

                    // Lưu đối tượng SavingsAccount đã cập nhật vào cơ sở dữ liệu
                    savingsAccountRepository.save(existingAccount);
                    return "Savings account updated successfully";
                } else {
                    return "Failed to update savings account: Interest rate not found for the term";
                }
            } else {
                return "Savings account not matured yet";
            }
        } else {
            return "Savings account not found";
        }
    }

    // Hàm chuyển đổi term từ string sang số tháng
    private int getTermInMonths(String term) {
        int months;
        String[] parts = term.split("\\s+");
        int length = parts.length;
        if (length == 2) {
            int num = Integer.parseInt(parts[0]);
            String unit = parts[1];
            months = switch (unit) {
                case "tháng" -> num;
                case "năm" -> num * 12;
                default -> 0;
            };
        } else {
            months = 0;
        }
        return months;
    }

    // Hàm chuyển đổi từ Date sang LocalDate
    private LocalDate convertToLocalDate(Date dateToConvert) {
        if (dateToConvert instanceof java.sql.Date) {
            return ((java.sql.Date) dateToConvert).toLocalDate();
        } else {
            return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    // Hàm chuyển đổi từ LocalDate sang Date
    private Date convertToDate(LocalDate localDateToConvert) {
        return Date.from(localDateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // khi đã rút sổ thành công thì cập nhật status
    @Override
    public String deleteSavingsAccount(Long id) {
        Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(id);
        if (savingsAccountOptional.isPresent()) {
            SavingsAccount existingAccount = savingsAccountOptional.get();

            // Lấy thời gian hiện tại
            LocalDate currentDate = LocalDate.now();
            // Chuyển đổi từ LocalDate sang Date
            Date maturityDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());



            // Cập nhật thông tin sổ tiết kiệm
            existingAccount.setMaturityDate(maturityDate);
            existingAccount.setStatus("Matured");

            // Tính lãi
            long numOfDay = daysBetween(existingAccount.getDepositDate(),maturityDate);
            InterestRate interestRate = interestRateService.getInterestRateByTerm("Không kỳ hạn");
            double interestRateValue = interestRate.getRate();
            //  nếu gửi và rút cùng ngày thì không có lãi
            if(numOfDay<=1){
                interestRateValue =0;
            }
            double depositAmount = existingAccount.getDepositAmount();
            double interestAmount = depositAmount * (interestRateValue/100) *((double) numOfDay /365);

            // Cộng lãi vào số dư
            existingAccount.setTotalAmount(depositAmount + interestAmount);
            Customer customer = existingAccount.getCustomer();
            Account account = customer.getAccount();
            double balance = account.getBalance();
            balance+= (depositAmount + interestAmount);
            account.setBalance(balance);
            customer.setAccount(account);
            existingAccount.setCustomer(customer);

            // Lưu đối tượng SavingsAccount đã cập nhật vào cơ sở dữ liệu
            savingsAccountRepository.save(existingAccount);

            return "Savings account matured successfully.";
        } else {
            return "Savings account not found";
        }
    }

    @Override
    public String updateAllSavingsAccounts() {
        // Lấy tất cả các sổ tiết kiệm trong hệ thống
        List<SavingsAccount> allSavingsAccounts = savingsAccountRepository.findSavingsAccountByStatus("active");

        // Duyệt qua từng sổ tiết kiệm để cập nhật
        for (SavingsAccount savingsAccount : allSavingsAccounts) {
           updateSavingsAccount(savingsAccount.getId());
        }
        return "update success!";
    }

    public static long daysBetween(Date startDate, Date endDate) {
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        long diffTime = endTime - startTime;
        return (diffTime / (1000 * 60 * 60 * 24)) + 1;
    }
}
