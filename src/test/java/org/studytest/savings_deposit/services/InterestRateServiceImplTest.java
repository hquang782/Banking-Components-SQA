/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.studytest.savings_deposit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.studytest.savings_deposit.mappers.InterestRateMapper;
import org.studytest.savings_deposit.models.InterestRate;
import org.studytest.savings_deposit.payload.InterestRateDTO;
import org.studytest.savings_deposit.repositories.InterestRateRepository;
import org.studytest.savings_deposit.services.Impl.InterestRateServiceImpl;

/**
 *
 * @author pc
 */
@SpringBootTest
public class InterestRateServiceImplTest {
    @Mock
    private InterestRateRepository interestRateRepo;
    @Autowired
    private InterestRateServiceImpl interestRateServiceImpl;
    @Mock
    private InterestRateMapper interestRateMapper;
    @InjectMocks
    private InterestRateServiceImpl mockinterestRateServiceImpl;
    @BeforeEach
    void setUp() {
    }
    @Test
    void testGetInterestRatebyIdExist(){
        long id=2;
        InterestRate mockInterestRate=new InterestRate(id, "6 tháng", 3.0);
        System.out.println(mockInterestRate.toString());
        when(interestRateRepo.findById(id)).thenReturn(Optional.of(mockInterestRate));
        InterestRate result=mockinterestRateServiceImpl.getInterestRateById(id);
        System.out.println(result.getRate());
        assertEquals(mockInterestRate, result);
        verify(interestRateRepo).findById(id);
    }
    @Test
    void testGetInterestRatebyIdExistDB(){
        long id=2;
        InterestRate result=interestRateServiceImpl.getInterestRateById(id);
        System.out.println(result.getRate());
        assertEquals(2, result.getId());
    }
    @Test
    void testGetInterestRatebyIdNotExist(){
        long id=-1;
        when(interestRateRepo.findById(id)).thenReturn(Optional.empty());
        InterestRate result=mockinterestRateServiceImpl.getInterestRateById(id);
        System.out.println(result);
        assertEquals(null, result);
        verify(interestRateRepo).findById(id);
    }
    @Test
    void testGetInterestRatebyIdNotExistDB(){
        long id=-1;
        InterestRate result=mockinterestRateServiceImpl.getInterestRateById(id);
        assertEquals(null, result);
    }
    @Test
    void testGetInterestRatebyTermExist(){
        String term="Không kỳ hạn";
        InterestRate mockInterestRate=new InterestRate(4L, term, 0.1);
        when(interestRateRepo.findByTerm(term)).thenReturn(mockInterestRate);
        InterestRate result=mockinterestRateServiceImpl.getInterestRateByTerm(term);
        assertEquals(mockInterestRate, result);
        verify(interestRateRepo).findByTerm(term);
    }
    @Test
    void testGetInterestRatebyTermExistDB(){
        String term="Không kỳ hạn";
        InterestRate result=interestRateServiceImpl.getInterestRateByTerm(term);
        assertEquals(4, result.getId());
        
    }
    @Test
    void testGetInterestRatebyTermNotExist(){
        String term="0 tháng";
        when(interestRateRepo.findByTerm(term)).thenReturn(null);
        InterestRate result=mockinterestRateServiceImpl.getInterestRateByTerm(term);
        assertEquals(null, result);
        verify(interestRateRepo).findByTerm(term);
    }
    @Test
    void testGetInterestRatebyTermNotExistDB(){
        String term="0 tháng";
        InterestRate result=interestRateServiceImpl.getInterestRateByTerm(term);
        assertEquals(null, result);

    }
    @Test
    void testGetAllInterestRate()
    {
        int total_interestrate=13;
        List<InterestRate> interestRates = new ArrayList<>();
        interestRates.add(new InterestRate(1L, "1 tháng", 1.7));
        interestRates.add(new InterestRate(2L, "6 tháng", 3.0));
        interestRates.add(new InterestRate(3L, "9 tháng", 3.0));
        interestRates.add(new InterestRate(4L, "Không kỳ hạn", 0.1));
        interestRates.add(new InterestRate(5L, "2 tháng", 1.7));
        interestRates.add(new InterestRate(6L, "3 tháng", 2.0));
        interestRates.add(new InterestRate(7L, "5 tháng", 2.0));
        interestRates.add(new InterestRate(8L, "12 tháng", 4.7));
        interestRates.add(new InterestRate(9L, "13 tháng", 4.7));
        interestRates.add(new InterestRate(10L, "15 tháng", 4.7));
        interestRates.add(new InterestRate(11L, "18 tháng", 4.7));
        interestRates.add(new InterestRate(12L, "24 tháng", 4.7));
        interestRates.add(new InterestRate(13L, "36 tháng", 4.7));
        when(interestRateRepo.findAll()).thenReturn(interestRates);
        List<InterestRateDTO> result=mockinterestRateServiceImpl.getAllInterestRates();
        System.out.println(result.size());
        assertNotNull(result);
        assertEquals(total_interestrate, result.size());
        verify(interestRateRepo).findAll();
    }
    @Test
    void testGetAllInterestRateDB()
    {
        int total_interestrate=13;
        List<InterestRateDTO> result=interestRateServiceImpl.getAllInterestRates();
        System.out.println(result.size());
        assertNotNull(result);
        assertEquals(total_interestrate, result.size());

    }
//    @Test
//    @Rollback
//    @Transactional
//    void testCreateInterestRate(){
//        InterestRateDTO interestRateDTO=new InterestRateDTO();
//        interestRateDTO.setRate(4.7);
//        interestRateDTO.setTerm("48 tháng");
//        InterestRate interestRate=new InterestRate();
//        interestRate.setRate(4.7);
//        interestRate.setTerm("48 tháng");
//        when(interestRateMapper.convertToDTO(interestRate)).thenReturn(interestRateDTO);
//        when(interestRateMapper.convertToEntity(interestRateDTO)).thenReturn(interestRate);
//        when(interestRateRepo.save(interestRate)).thenReturn(interestRate);
//        InterestRateDTO res=mockinterestRateServiceImpl.createInterestRate(interestRateDTO);
//        System.out.println(res.getTerm());
//        assertEquals(interestRateDTO, res);
//        verify(interestRateRepo).save(interestRate);
//    }
//    @Test
//    @Rollback
////    @Transactional
//    void testCreateInterestRateDB(){
//        InterestRateDTO interestRateDTO=new InterestRateDTO();
//        interestRateDTO.setRate(4.7);
//        interestRateDTO.setTerm("48 tháng");
//        InterestRateDTO res=interestRateServiceImpl.createInterestRate(interestRateDTO);
//        System.out.println(res.getTerm());
//        assertEquals(interestRateDTO.getRate(), res.getRate());
//        assertEquals(interestRateDTO.getTerm(), res.getTerm());
//    }
//    @Test
//    @Rollback
//    @Transactional
//    void testUpdateInterestRate_Rate(){
//        long id=1;
//        InterestRate interestRate=new InterestRate(id, "1 tháng", 1.7);
//        InterestRateDTO interestRateDTO=new InterestRateDTO(id, "1 tháng", 1.8);
//        when(interestRateMapper.convertToDTO(interestRate)).thenReturn(interestRateDTO);
//        when(interestRateRepo.findById(id)).thenReturn(Optional.of(interestRate));
//        when(interestRateRepo.save(interestRate)).thenReturn(interestRate);
//        InterestRateDTO res=mockinterestRateServiceImpl.updateInterestRate(id, interestRateDTO);
//        System.out.println(res.getRate());
//        assertEquals(interestRateDTO, res);
//        verify(interestRateRepo).save(interestRate);
//    }
//    @Test
//    @Rollback
//    @Transactional
//    void testUpdateInterestRate_RateDB(){
//        long id=1;
//        InterestRateDTO interestRateDTO=new InterestRateDTO(id, "1 tháng", 1.8);
//        InterestRateDTO res=interestRateServiceImpl.updateInterestRate(id, interestRateDTO);
//        System.out.println(res.getRate());
//        assertEquals(interestRateDTO.getId(), res.getId());
//    }
//    @Test
//    @Rollback
//    @Transactional
//    void testUpdateInterestRate_Term(){
//        long id=13;
//        InterestRate interestRate=new InterestRate(id, "36 tháng", 4.7);
//        InterestRateDTO interestRateDTO=new InterestRateDTO(id, "40 tháng", 4.8);
//        when(interestRateMapper.convertToDTO(interestRate)).thenReturn(interestRateDTO);
//        when(interestRateRepo.findById(id)).thenReturn(Optional.of(interestRate));
//        when(interestRateRepo.save(interestRate)).thenReturn(interestRate);
//        InterestRateDTO res=mockinterestRateServiceImpl.updateInterestRate(id, interestRateDTO);
//        System.out.println(res.getTerm());
//        assertEquals(interestRateDTO, res);
//    }
//    @Test
//    @Rollback
//    @Transactional
//    void testUpdateInterestRateNotExist(){
//        long id=-1;
//        InterestRateDTO irdto=new InterestRateDTO(id, "40 tháng", 4.8);
//        when(interestRateRepo.findById(id)).thenReturn(Optional.empty());
//        InterestRateDTO res=mockinterestRateServiceImpl.updateInterestRate(id, irdto);
//        assertEquals(null, res);
//        verify(interestRateRepo).findById(id);
//    }
}
