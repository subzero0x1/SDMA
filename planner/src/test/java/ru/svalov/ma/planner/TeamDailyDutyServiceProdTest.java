package ru.svalov.ma.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.svalov.ProdConfig;

import java.time.LocalDate;

@ContextConfiguration(classes = ProdConfig.class)
@Test(enabled = false)
public class TeamDailyDutyServiceProdTest extends AbstractTestNGSpringContextTests {

    @Autowired
    TeamDailyDutyService teamDailyDutyService;

    private final LocalDate startDate = LocalDate.of(2016, 9, 15);
    private final LocalDate endDate = startDate.plusMonths(1);


    @Test
    public void testCreateBoxDevSchedule() throws Exception {
        int createdEventsCount = teamDailyDutyService.createBoxDevSchedule(startDate, endDate);
        assert createdEventsCount > 0;
    }

    @Test
    public void testCreateArchitectSchedule() throws Exception {
        int createdEventsCount = teamDailyDutyService.createArchitectSchedule(startDate, endDate);
        assert createdEventsCount > 0;
    }

    @Test
    public void testCreateRetailSchedule() throws Exception {
        int createdEventsCount = teamDailyDutyService.createRetailSchedule(startDate, endDate);
        assert createdEventsCount > 0;
    }

    @Test
    public void testCreateRetailFrontSchedule() throws Exception {
        int createdEventsCount = teamDailyDutyService.createRetailFrontSchedule(startDate, endDate);
        assert createdEventsCount > 0;
    }
}