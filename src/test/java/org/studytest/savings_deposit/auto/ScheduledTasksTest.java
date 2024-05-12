package org.studytest.savings_deposit.auto;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.studytest.savings_deposit.services.SavingsAccountService;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ScheduledTasksTest {
    @InjectMocks
    private ScheduledTasks scheduledTasks;

    @Mock
    private SavingsAccountService savingsAccountService;
    @Test
    @Rollback
    public void testUpdateAllSavingsAccounts() {
        scheduledTasks.updateAllSavingsAccounts();
        verify(savingsAccountService, times(1)).updateAllSavingsAccounts();
    }
}
