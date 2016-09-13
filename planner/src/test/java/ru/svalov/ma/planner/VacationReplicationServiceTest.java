package ru.svalov.ma.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.svalov.ProdConfig;
import ru.svalov.ma.model.Employee;
import ru.svalov.util.InfiniteIterator;

import java.time.LocalDate;

@ContextConfiguration(classes = ProdConfig.class)
@Test
public class VacationReplicationServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private VacationReplicationService replicationService;
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testReplicateVacations() throws Exception {
        final InfiniteIterator<Employee> empIt = employeeService.readEmpoyees(Employee::isDailyDuty);

        replicationService.replicateVacations(empIt.toImmutableList(), LocalDate.of(2016, 9, 26), LocalDate.of(2016, 9, 30));
    }

}