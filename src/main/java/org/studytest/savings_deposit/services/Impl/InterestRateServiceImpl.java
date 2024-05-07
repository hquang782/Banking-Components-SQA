package org.studytest.savings_deposit.services.Impl;

import org.springframework.stereotype.Service;
import org.studytest.savings_deposit.mappers.InterestRateMapper;
import org.studytest.savings_deposit.models.InterestRate;
import org.studytest.savings_deposit.payload.InterestRateDTO;
import org.studytest.savings_deposit.repositories.InterestRateRepository;
import org.studytest.savings_deposit.services.InterestRateService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterestRateServiceImpl implements InterestRateService {
    private final InterestRateRepository interestRateRepository;

    private final InterestRateMapper interestRateMapper;

    public InterestRateServiceImpl(InterestRateRepository interestRateRepository, InterestRateMapper interestRateMapper) {
        this.interestRateRepository = interestRateRepository;
        this.interestRateMapper = interestRateMapper;
    }

    @Override
    public InterestRate getInterestRateById(Long id) {
        Optional<InterestRate> interestRateOptional = interestRateRepository.findById(id);
        return interestRateOptional.orElse(null);
    }

    // lấy thông tin lãi suất từ kỳ hạn
    @Override
    public InterestRate getInterestRateByTerm(String term) {

        InterestRate interestRate =  interestRateRepository.findByTerm(term);
        if (interestRate != null) {
            // In ra thông tin của lãi suất
            System.out.println("Thông tin lãi suất: " + interestRate);
        } else {
            // In ra thông báo cho biết không tìm thấy lãi suất
            System.out.println("Không tìm thấy lãi suất cho kỳ hạn: " + term);
        }
        return interestRate ;
    }

    @Override
    public List<InterestRateDTO> getAllInterestRates() {
        List<InterestRate> interestRates = interestRateRepository.findAll();
        return interestRates.stream().map(interestRateMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public InterestRateDTO createInterestRate(InterestRateDTO interestRateDTO) {
        InterestRate interestRate = interestRateMapper.convertToEntity(interestRateDTO);
        InterestRate savedInterestRate = interestRateRepository.save(interestRate);
        return interestRateMapper.convertToDTO(savedInterestRate);
    }

    @Override
    public InterestRateDTO updateInterestRate(Long id, InterestRateDTO updatedInterestRateDTO) {
        Optional<InterestRate> existingInterestRateOptional = interestRateRepository.findById(id);
        if (existingInterestRateOptional.isPresent()) {
            InterestRate existingInterestRate = existingInterestRateOptional.get();
            existingInterestRate.setTerm(updatedInterestRateDTO.getTerm());
            existingInterestRate.setRate(updatedInterestRateDTO.getRate());
            InterestRate updatedInterestRate = interestRateRepository.save(existingInterestRate);
            return interestRateMapper.convertToDTO(updatedInterestRate);
        } else {
            return null;
        }
    }

    @Override
    public String deleteInterestRate(Long id) {
        Optional<InterestRate> interestRateOptional = interestRateRepository.findById(id);
        if (interestRateOptional.isPresent()) {
            interestRateRepository.deleteById(id);
            return "Interest rate deleted successfully";
        } else {
            return "Interest rate not found";
        }
    }
}


