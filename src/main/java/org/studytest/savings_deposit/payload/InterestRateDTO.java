package org.studytest.savings_deposit.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestRateDTO {
    private Long id;
    private String term;
    private Double rate;
}