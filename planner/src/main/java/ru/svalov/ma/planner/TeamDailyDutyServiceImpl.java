package ru.svalov.ma.planner;

import org.springframework.beans.factory.annotation.Autowired;
import ru.svalov.ma.model.Employee;
import ru.svalov.util.InfiniteIterator;

import java.time.LocalDate;

public class TeamDailyDutyServiceImpl implements TeamDailyDutyService {

    private DailyDutyService service;
    private EmployeeService employeeService;
    private CalendarEventsService dailyDutyEventsService;
    private CalendarEventsService architectDDEventsService;
    private CalendarEventsService retailDDEventsService;
    private CalendarEventsService retailFrontDDEventsService;

    @Autowired
    public TeamDailyDutyServiceImpl(DailyDutyService service, EmployeeService employeeService, CalendarEventsService dailyDutyEventsService,
                                    CalendarEventsService architectDDEventsService, CalendarEventsService retailDDEventsService,
                                    CalendarEventsService retailFrontDDEventsService) {
        this.service = service;
        this.employeeService = employeeService;
        this.dailyDutyEventsService = dailyDutyEventsService;
        this.architectDDEventsService = architectDDEventsService;
        this.retailDDEventsService = retailDDEventsService;
        this.retailFrontDDEventsService = retailFrontDDEventsService;
    }

    @Override
    public int createBoxDevSchedule(LocalDate start, LocalDate end) {
        final InfiniteIterator<Employee> empIt = employeeService.readEmpoyees(Employee::isDailyDuty);
        return service.schedule(start, end, empIt, dailyDutyEventsService);
    }

    @Override
    public int createArchitectSchedule(LocalDate start, LocalDate end) {
        final InfiniteIterator<Employee> archIt = employeeService.readEmpoyees(Employee::isArchitectDailyDuty);
        return service.schedule(start, end, archIt, architectDDEventsService);
    }

    @Override
    public int createRetailSchedule(LocalDate start, LocalDate end) {
        final InfiniteIterator<Employee> retIt = employeeService.readEmpoyees(Employee::isRetailDailyDuty);
        return service.schedule(start, end, retIt, retailDDEventsService);
    }

    @Override
    public int createRetailFrontSchedule(LocalDate start, LocalDate end) {
        final InfiniteIterator<Employee> retIt = employeeService.readEmpoyees(Employee::isRetailFrontDailyDuty);
        return service.schedule(start, end, retIt, retailFrontDDEventsService);
    }
}
