package ru.svalov.ma.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.svalov.ProdConfig;
import ru.svalov.ma.data.EmployeesProvider;
import ru.svalov.ma.model.CalendarEvents;
import ru.svalov.ma.model.Employee;
import ru.svalov.util.InfiniteIterator;

import java.time.LocalDate;

@ContextConfiguration(classes = ProdConfig.class)
public class DailyDutyServiceProdTestCase extends AbstractTestNGSpringContextTests {

    @Autowired
    private DailyDutyService service;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CalendarEventsService dailyDutyEventsService;


    @Test
    public void testBuild() {
        final LocalDate startDate = LocalDate.of(2016, 10, 16);
//        final LocalDate startDate = LocalDate.now();
        final LocalDate endDate = startDate.plusMonths(1);

        final InfiniteIterator<Employee> empIt = employeeService.readEmpoyees(Employee::isDailyDuty);
        service.schedule(startDate, endDate, empIt, dailyDutyEventsService);
    }
}
