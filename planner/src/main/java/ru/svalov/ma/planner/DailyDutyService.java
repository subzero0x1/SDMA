package ru.svalov.ma.planner;

import ru.svalov.ma.data.EmployeesProvider;
import ru.svalov.ma.model.*;
import ru.svalov.util.InfiniteIterator;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

public class DailyDutyService {

    // todo : add logging and performance measure

    private EmployeesProvider employeesProvider;

    private CalendarEventsService dailyDutyEventsService;
    private CalendarEventsService architectDDEventsService;
    private CalendarEventsService laborEventsService;
    private CalendarEventsService holidayEventsService;

    private int historyLookupDays = 20;
    private boolean parallelDataLoad = false;

    private static boolean isEqualEvent(Employee employee, List<CalendarEvent> events) {
        return events.size() == 1 &&
                employee.getLogin().equals(events.iterator().next().getSubject());
    }

    private static Employee findNextForDailyDuty(InfiniteIterator<Employee> empIt, CalendarEvents holidayEvents, LocalDate currentDate) {
        Employee employee = empIt.next();
        while (holidayEvents.contains(currentDate, EventType.VACATION, employee.getLogin()) ||
                holidayEvents.contains(currentDate, EventType.SICK, employee.getLogin())) {
            employee = empIt.next();
        }
        return employee;
    }

    public void schedule(final LocalDate startDate, final LocalDate endDate) {
        Objects.requireNonNull(startDate, "startDate");
        Objects.requireNonNull(endDate, "endDate");
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate is after endDate");
        }

        final InfiniteIterator<Employee> empIt;
        final InfiniteIterator<Employee> archIt;
        CalendarEvents dailyDutyEvents;
        CalendarEvents architectDailyDutyEvents;
        LaborCalendarEvents laborEvents;
        CalendarEvents holidayEvents;
        if (parallelDataLoad) {
            // todo : call providers in separate threads
//            empIt = null;
//            dailyDutyEvents = null;
//            laborEvents = null;
//            holidayEvents = null;
            throw new UnsupportedOperationException();
        } else {
            empIt = readEmployees(startDate, endDate);
            archIt = readArchitects(startDate, endDate);

            // find last employee was on duty
            final String lastOnDuty = readLastOnDuty(startDate);

            // rewind iterator to last employee was on duty
            while (lastOnDuty != null && empIt.hasNext()) {
                if (empIt.next().getLogin().equals(lastOnDuty)) {
                    break;
                }
            }

            dailyDutyEvents = dailyDutyEventsService.get(startDate, endDate);
            architectDailyDutyEvents = architectDDEventsService.get(startDate, endDate);
            laborEvents = new LaborCalendarEvents(laborEventsService.get(startDate, endDate));
            holidayEvents = holidayEventsService.get(startDate, endDate);
        }

        Stream.iterate(startDate, d -> d.plusDays(1))
                .limit(startDate.until(endDate, DAYS))
                .forEach(currentDate -> {
                            if (laborEvents.isWeekendOrHoliday(currentDate)) {
                                deleteOutdatedEvents(dailyDutyEvents, currentDate);
                                deleteOutdatedEvents(architectDailyDutyEvents, currentDate);
                            } else {
                                processDate(empIt, dailyDutyEvents, holidayEvents, currentDate, EventType.DAILY_DUTY, dailyDutyEventsService);
                                processDate(archIt, architectDailyDutyEvents, holidayEvents, currentDate, EventType.ARCH_DAILY_DUTY, architectDDEventsService);
                            }
                        }
                );
    }

    private void processDate(InfiniteIterator<Employee> empIt, CalendarEvents dailyDutyEvents, CalendarEvents holidayEvents, LocalDate currentDate, EventType dailyDuty, CalendarEventsService dailyDutyEventsService) {
        Employee employee = findNextForDailyDuty(empIt, holidayEvents, currentDate);
        List<CalendarEvent> events = dailyDutyEvents.get(currentDate, dailyDuty);
        if (!isEqualEvent(employee, events)) {
            if (!events.isEmpty()) {
                deleteOutdatedEvents(dailyDutyEvents, currentDate);
            }
            createEvent(dailyDutyEventsService, currentDate, employee);
        }
    }

    private void createEvent(CalendarEventsService eventsService, LocalDate currentDate, Employee employee) {
        CalendarEvent event = new CalendarEvent();
        event.setDate(currentDate);
        event.setType(EventType.DAILY_DUTY);
        event.setSubject(employee.getLogin());
        event.getAttendees().add(employee);
        eventsService.create(event);
    }

    private String readLastOnDuty(LocalDate startDate) {
        final Iterator<CalendarEvent> lastOnDutyIterator = dailyDutyEventsService.get(
                startDate.minusDays(historyLookupDays),
                startDate
        )
                .getLast()
                .stream()
                .filter(e -> EventType.DAILY_DUTY.equals(e.getType()))
                .limit(1)
                .iterator();
        return lastOnDutyIterator.hasNext() ? lastOnDutyIterator.next().getSubject() : null;
    }

    private InfiniteIterator<Employee> readEmployees(LocalDate startDate, LocalDate endDate) {
        return new InfiniteIterator<>(employeesProvider
                .get(startDate, endDate)
                .filter(Employee::isDailyDuty)
                .sorted((e1, e2) -> e1.getLogin().compareTo(e2.getLogin()))
                .collect(Collectors.toList())
        );
    }

    private InfiniteIterator<Employee> readArchitects(LocalDate startDate, LocalDate endDate) {
        return new InfiniteIterator<>(employeesProvider
                .get(startDate, endDate)
                .filter(Employee::isArchitectDailyDuty)
                .sorted((e1, e2) -> e1.getLogin().compareTo(e2.getLogin()))
                .collect(Collectors.toList())
        );
    }

    private void deleteOutdatedEvents(CalendarEvents events, LocalDate date) {
        if (events.contains(date, EventType.DAILY_DUTY)) {
            List<CalendarEvent> deprecated = events.get(date, EventType.DAILY_DUTY);
            for (CalendarEvent d : deprecated) {
                dailyDutyEventsService.delete(d.getId());
            }
        }
    }

    public EmployeesProvider getEmployeesProvider() {
        return employeesProvider;
    }

    public void setEmployeesProvider(EmployeesProvider employeesProvider) {
        this.employeesProvider = employeesProvider;
    }

    public CalendarEventsService getDailyDutyEventsService() {
        return dailyDutyEventsService;
    }

    public void setDailyDutyEventsService(CalendarEventsService dailyDutyEventsService) {
        this.dailyDutyEventsService = dailyDutyEventsService;
    }

    public CalendarEventsService getLaborEventsService() {
        return laborEventsService;
    }

    public void setLaborEventsService(CalendarEventsService laborEventsService) {
        this.laborEventsService = laborEventsService;
    }

    public CalendarEventsService getHolidayEventsService() {
        return holidayEventsService;
    }

    public void setHolidayEventsService(CalendarEventsService holidayEventsService) {
        this.holidayEventsService = holidayEventsService;
    }

    public int getHistoryLookupDays() {
        return historyLookupDays;
    }

    public void setHistoryLookupDays(int historyLookupDays) {
        this.historyLookupDays = historyLookupDays;
    }

    public boolean isParallelDataLoad() {
        return parallelDataLoad;
    }

    public void setParallelDataLoad(boolean parallelDataLoad) {
        this.parallelDataLoad = parallelDataLoad;
    }

    public CalendarEventsService getArchitectDDEventsService() {
        return architectDDEventsService;
    }

    public void setArchitectDDEventsService(CalendarEventsService architectDDEventsService) {
        this.architectDDEventsService = architectDDEventsService;
    }
}
