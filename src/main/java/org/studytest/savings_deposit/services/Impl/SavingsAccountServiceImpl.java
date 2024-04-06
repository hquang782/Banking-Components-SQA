package org.studytest.savings_deposit.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studytest.savings_deposit.mappers.SavingsAccountMapper;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {
    private final SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private SavingsAccountMapper savingsAccountMapper;

    @Autowired
    private InterestRateService interestRateService;

    @Autowired
    private CustomerService customerService;
    @Autowired
    public SavingsAccountServiceImpl(SavingsAccountRepository savingsAccountRepository,CustomerService customerService,InterestRateService interestRateService) {
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
    public List<SavingsAccount> getAllSavingsAccountsByCustomer(String identification_number){
        Optional<Customer> customer = customerService.getCustomerByIdentificationNumber(identification_number);
        return getAllSavingsAccountsByCustomerId(customer.get().getId());
    }


    @Override
    public String createSavingsAccount(Long customerId, SavingsAccountDTO savingsAccountDTO) {

        InterestRate interestRate = interestRateService.getInterestRateById(savingsAccountDTO.getInterestRateId());
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if (interestRate != null) {
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
            // TODO lính lãi
//            savingsAccount.setTotalAmount(savingsAccountDTO.getTotalAmount());
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

    // TODO cập nhật lãi sau mỗi chu kì
    @Override
    public String  updateSavingsAccount(Long id) {
        Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(id);
        if (savingsAccountOptional.isPresent()) {
            // Cập nhật thông tin của tài khoản tiết kiệm đã tồn tại với thông tin từ updatedSavingsAccount
            SavingsAccount existingAccount = savingsAccountOptional.get();
            double tienGoc = existingAccount.getCustomer().getAccount().getBalance();
            //double tienLai = tienGoc + lãi(% theo kì hạn)
            // thành tiền = tienGoc + tienLai
            //lấy lãi mới
            InterestRate interestRate = interestRateService.getInterestRateById(existingAccount.getInterestRate().getId());
            existingAccount.setInterestRateValue(interestRate.getRate());

            // Lưu đối tượng SavingsAccount đã cập nhật vào cơ sở dữ liệu
            savingsAccountRepository.save(existingAccount);
            return "Savings account updated successfully";
        } else {
            return "Savings account not found";
        }
    }
    // khi đã rút sổ thành công thì cập nhật status
    @Override
    public String deleteSavingsAccount(Long id) {
        Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(id);
        if (savingsAccountOptional.isPresent()) {
            // Xóa tài khoản tiết kiệm từ cơ sở dữ liệu
//            savingsAccountRepository.deleteById(id);
            // lấy tgian hiện tại
            LocalDate currentDate = LocalDate.now();
            // Chuyển đổi từ LocalDate sang Date
            Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            SavingsAccount existingAccount = savingsAccountOptional.get();
            existingAccount.setMaturityDate(date);
            existingAccount.setStatus("Matured");
            double tienGoc = existingAccount.getCustomer().getAccount().getBalance();
            Date dayStart = existingAccount.getDepositDate();
            Date dayEnd = existingAccount.getMaturityDate();
            //TODO: sơn code công thức tính lãi vô đây nhá :)))
//            double tienLai =  lãi(% theo không kì hạn || có kì hạn nếu rút đúng chu kì)
            // thành tiền = tienGoc + tienLai
            // Lưu đối tượng SavingsAccount đã cập nhật vào cơ sở dữ liệu
            savingsAccountRepository.save(existingAccount);

            return "Savings account Matured successfully";
        } else {
            return "Savings account not found";
        }
    }
    public static long daysBetween(Date startDate, Date endDate) {
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        long diffTime = endTime - startTime;
        return (diffTime / (1000 * 60 * 60 * 24))+1;
    }
}
