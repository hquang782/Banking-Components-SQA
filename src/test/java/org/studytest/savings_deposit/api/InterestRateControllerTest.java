/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.studytest.savings_deposit.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.studytest.savings_deposit.models.InterestRate;
import org.studytest.savings_deposit.payload.InterestRateDTO;
import org.studytest.savings_deposit.repositories.InterestRateRepository;
import org.studytest.savings_deposit.services.Impl.InterestRateServiceImpl;
import org.studytest.savings_deposit.services.InterestRateService;

/**
 *
 * @author pc
 */@SpringBootTest
public class InterestRateControllerTest {
    @Autowired
    private InterestRateController irc;
    @InjectMocks
    private InterestRateController mockirc;
    @Mock
    private InterestRateService mockinterestRateService;
    @Test
    public void testApiGetByIdExist(){
        long id=2;
        InterestRate mockInterestRate=new InterestRate(id, "6 tháng", 3.0);
        System.out.println(mockInterestRate.toString());
        when(mockinterestRateService.getInterestRateById(id)).thenReturn(mockInterestRate);
        ResponseEntity<InterestRate> response=mockirc.getInterestRateById(id);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    public void testApiGetByIdExistDB(){
        long id=2;
        ResponseEntity<InterestRate> response=irc.getInterestRateById(id);
        assertNotNull(response.getBody());
        assertEquals(2,response.getBody().getId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    public void testApiGetByIdNotExist(){
        long id=-1;
        when(mockinterestRateService.getInterestRateById(id)).thenReturn(null);
        ResponseEntity<InterestRate> response=mockirc.getInterestRateById(id);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    public void testApiGetByIdNotExistDB(){
        long id=-1;
        ResponseEntity<InterestRate> response=irc.getInterestRateById(id);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    public void testApiGetByTermExist(){
        String term="Không kỳ hạn";
        InterestRate mockInterestRate=new InterestRate(4L, term, 0.1);
        when(mockinterestRateService.getInterestRateByTerm(term)).thenReturn(mockInterestRate);
        ResponseEntity<InterestRate> response=mockirc.getInterestRateByTerm(term);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    public void testApiGetByTermExistDB(){
        String term="Không kỳ hạn";
        ResponseEntity<InterestRate> response=irc.getInterestRateByTerm(term);
        assertNotNull(response.getBody());
        assertEquals(4,response.getBody().getId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    public void testApiGetByTermNotExist(){
        String term="0 tháng";
        when(mockinterestRateService.getInterestRateByTerm(term)).thenReturn(null);
        ResponseEntity<InterestRate> response=mockirc.getInterestRateByTerm(term);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
    @Test
    public void testApiGetByTermNotExistDB(){
        String term="0 tháng";
        ResponseEntity<InterestRate> response=irc.getInterestRateByTerm(term);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
    @Test
    public void testApiGetAll(){
        int total_interestrate=13;
        List<InterestRateDTO> interestRates = new ArrayList<>();
        interestRates.add(new InterestRateDTO(1L, "1 tháng", 1.7));
        interestRates.add(new InterestRateDTO(2L, "6 tháng", 3.0));
        interestRates.add(new InterestRateDTO(3L, "9 tháng", 3.0));
        interestRates.add(new InterestRateDTO(4L, "Không kỳ hạn", 0.1));
        interestRates.add(new InterestRateDTO(5L, "2 tháng", 1.7));
        interestRates.add(new InterestRateDTO(6L, "3 tháng", 2.0));
        interestRates.add(new InterestRateDTO(7L, "5 tháng", 2.0));
        interestRates.add(new InterestRateDTO(8L, "12 tháng", 4.7));
        interestRates.add(new InterestRateDTO(9L, "13 tháng", 4.7));
        interestRates.add(new InterestRateDTO(10L, "15 tháng", 4.7));
        interestRates.add(new InterestRateDTO(11L, "18 tháng", 4.7));
        interestRates.add(new InterestRateDTO(12L, "24 tháng", 4.7));
        interestRates.add(new InterestRateDTO(13L, "36 tháng", 4.7));
        when(mockinterestRateService.getAllInterestRates()).thenReturn(interestRates);
        ResponseEntity<List<InterestRateDTO>> response=mockirc.getAllInterestRates();
        assertEquals(total_interestrate, response.getBody().size());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    public void testApiGetAllDB(){
        ResponseEntity<List<InterestRateDTO>> response=irc.getAllInterestRates();
        assertEquals(13, response.getBody().size());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
//    @Test
////    @Transactional
////    @Rollback
//    public void testApiCreate(){
//        InterestRateDTO interestRateDTO=new InterestRateDTO();
//        interestRateDTO.setRate(4.7);
//        interestRateDTO.setTerm("48 tháng");
//        InterestRate interestRate=new InterestRate();
//        when(mockinterestRateService.createInterestRate(interestRateDTO)).thenReturn(interestRateDTO);
//        ResponseEntity<InterestRateDTO> response=irc.createInterestRate(interestRateDTO);
//        assertEquals(HttpStatus.CREATED,response.getStatusCode());
//    }
    
}
