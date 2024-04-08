package org.studytest.savings_deposit.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.studytest.savings_deposit.models.InterestRate;
import org.studytest.savings_deposit.payload.InterestRateDTO;
import org.studytest.savings_deposit.services.InterestRateService;
import java.util.List;
@RestController
@RequestMapping("/api/v1/interest-rate")
public class InterestRateController {

    @Autowired
    private InterestRateService interestRateService;

    @GetMapping("/{id}")
    public ResponseEntity<InterestRate> getInterestRateById(@PathVariable Long id) {
        InterestRate interestRate = interestRateService.getInterestRateById(id);
        if (interestRate != null) {
            return new ResponseEntity<>(interestRate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // lấy thông tin lãi suất bằng kỳ hạn
    @GetMapping("/term={term}")
    public ResponseEntity<InterestRate> getInterestRateByTerm(@PathVariable String term) {
        InterestRate interestRate = interestRateService.getInterestRateByTerm(term) ;
        if (interestRate != null) {
            return new ResponseEntity<>(interestRate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<InterestRateDTO>> getAllInterestRates() {
        List<InterestRateDTO> interestRateDTOs = interestRateService.getAllInterestRates();
        return new ResponseEntity<>(interestRateDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InterestRateDTO> createInterestRate(@RequestBody InterestRateDTO interestRateDTO) {
        InterestRateDTO createdInterestRateDTO = interestRateService.createInterestRate(interestRateDTO);
        return new ResponseEntity<>(createdInterestRateDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterestRateDTO> updateInterestRate(@PathVariable Long id, @RequestBody InterestRateDTO updatedInterestRateDTO) {
        InterestRateDTO updatedInterestRate = interestRateService.updateInterestRate(id, updatedInterestRateDTO);
        if (updatedInterestRate != null) {
            return new ResponseEntity<>(updatedInterestRate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInterestRate(@PathVariable Long id) {
        String result = interestRateService.deleteInterestRate(id);
        if (result.equals("Interest rate deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}
