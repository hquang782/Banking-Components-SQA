package org.studytest.savings_deposit.api;


import junit.framework.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.studytest.savings_deposit.services.SavingsAccountService;
import org.studytest.savings_deposit.models.SavingsAccount;

@SpringBootTest
public class SavingAccountControllerTest {
    @Mock
    SavingsAccountService mocksavingsAccountService;
    @InjectMocks
    SavingsAccountController sac;
    @Autowired
    SavingsAccountController savingsAccountc;

    @Test
    public void testGetById()
    {
        SavingsAccount savingAccount = new SavingsAccount();
        savingAccount.setId(1L);
        savingAccount.setAccountNumber("e7078e3a-0f1b-43be-bb22-2279ec46e7ef");
        Mockito.when(mocksavingsAccountService.getSavingsAccountById(1L)).thenReturn(savingAccount);
        ResponseEntity<SavingsAccount> res=sac.getSavingsAccountById(1L);
        assertEquals(HttpStatus.OK,res.getStatusCode());
        assertNotNull(res.getBody());

    }
    @Test
    public void testGetByIdDB()
    {
        ResponseEntity<SavingsAccount> res=savingsAccountc.getSavingsAccountById(4L);
        assertEquals(HttpStatus.OK,res.getStatusCode());
        assertNotNull(res.getBody());
        assertEquals(4,res.getBody().getId());

    }
    @Test
    public void testGetByIdNotExist()
    {
        Mockito.when(mocksavingsAccountService.getSavingsAccountById(-1L)).thenReturn(null);
        ResponseEntity<SavingsAccount> res=sac.getSavingsAccountById(-1L);
        assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());
        assertNull(res.getBody());

    }
    @Test
    public void testGetByIdNotExistDB()
    {
        ResponseEntity<SavingsAccount> res=savingsAccountc.getSavingsAccountById(1L);
        assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());
        assertNull(res.getBody());
    }
    @Test
    @Transactional
    @Rollback
    public void testUpdate()
    {
        ResponseEntity<String> res=savingsAccountc.updateSavingsAccount(4L);
        assertEquals("Savings account not matured yet", res.getBody());
        assertNotNull(res.getBody());
        assertEquals(HttpStatus.OK,res.getStatusCode());
    }
    @Test
    @Rollback
    public void testUpdateNotMaturedDate()
    {
        ResponseEntity<String> res=savingsAccountc.updateSavingsAccount(4L);
        assertEquals("Savings account not matured yet", res.getBody());
        assertNotNull(res.getBody());
        assertEquals(HttpStatus.OK,res.getStatusCode());
    }
    @Test
    public void testUpdateNotFound()
    {
        ResponseEntity<String> res=savingsAccountc.updateSavingsAccount(-1L);
        System.out.println(res.getBody());
        assertEquals("Savings account not found", res.getBody());
    }
    @Test
    @Transactional
    @Rollback
    public void testDelete()
    {
        ResponseEntity<String> res=savingsAccountc.deleteSavingsAccount(4L);
        assertNotNull(res.getBody());
        assertEquals("Savings account matured successfully.", res.getBody());

    }
    @Test
    @Transactional
    @Rollback
    public void testDeleteNotFound()
    {
        ResponseEntity<String> res=savingsAccountc.deleteSavingsAccount(-1L);
        assertNotNull(res.getBody());
        assertEquals("Savings account not found", res.getBody());
    }

}