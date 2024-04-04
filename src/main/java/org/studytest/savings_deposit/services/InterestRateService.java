package org.studytest.savings_deposit.services;

import org.studytest.savings_deposit.models.InterestRate;
import org.studytest.savings_deposit.payload.InterestRateDTO;

import java.util.List;

public interface InterestRateService {
    InterestRate getInterestRateById(Long id);
    List<InterestRateDTO> getAllInterestRates();
    InterestRateDTO createInterestRate(InterestRateDTO interestRateDTO);
    InterestRateDTO updateInterestRate(Long id, InterestRateDTO updatedInterestRateDTO);
    String deleteInterestRate(Long id);
}
