package org.studytest.savings_deposit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studytest.savings_deposit.models.InterestRate;

public interface InterestRateRepository extends JpaRepository<InterestRate,Long> {
    InterestRate findByTerm(String term);
}
