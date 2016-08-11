package ru.svalov.ma.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.time.LocalDate;

@ContextConfiguration(locations = {"classpath:spring-prod-config-google.xml"})
public class DailyDutyServiceProdTestCase extends AbstractTestNGSpringContextTests {

    @Autowired
    private DailyDutyService service;

    @Test
    public void testBuild() {
        final LocalDate startDate = LocalDate.of(2016, 8, 15);
//        final LocalDate startDate = LocalDate.now();
        final LocalDate endDate = startDate.plusMonths(1);

        service.schedule(startDate, endDate);
    }
}
