package ru.svalov.ma.planner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(DailyDutyService.class);

    // todo : add logging and performance measure

    private CalendarEventsService laborEventsService;
    private CalendarEventsService holidayEventsService;

    private int historyLookupDays = 20;
    private boolean parallelDataLoad = false;

    public DailyDutyService(CalendarEventsService laborEventsService, CalendarEventsService holidayEventsService) {
        this.laborEventsService = laborEventsService;
        this.holidayEventsService = holidayEventsService;
    }

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

    public int schedule(final LocalDate startDate, final LocalDate endDate, InfiniteIterator<Employee> empIt, CalendarEventsService
            dailyDutyEventsService) {
        Objects.requireNonNull(startDate, "startDate");
        Objects.requireNonNull(endDate, "endDate");
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate is after endDate");
        }

        LaborCalendarEvents laborEvents;
        CalendarEvents holidayEvents;
        if (parallelDataLoad) {
            // todo : call providers in separate threads
            throw new UnsupportedOperationException();
        } else {
            // find last employee was on duty
            final String lastOnDuty = readLastOnDuty(startDate, dailyDutyEventsService);

            // rewind iterator to last employee was on duty
            while (lastOnDuty != null && empIt.hasNext()) {
                if (empIt.next().getLogin().equals(lastOnDuty)) {
                    break;
                }
            }

            laborEvents = new LaborCalendarEvents(laborEventsService.get(startDate, endDate));
            holidayEvents = holidayEventsService.get(startDate, endDate);
        }

        CalendarEvents dailyDutyEvents = dailyDutyEventsService.get(startDate, endDate);
        return Stream.iterate(startDate, d -> d.plusDays(1))
                .limit(startDate.until(endDate, DAYS))
                .mapToInt(currentDate -> {
                            if (laborEvents.isWeekendOrHoliday(currentDate)) {
                                deleteOutdatedEvents(dailyDutyEvents, currentDate, dailyDutyEventsService);
                            } else {
                                return processDate(empIt, dailyDutyEvents, holidayEvents, currentDate, EventType.DAILY_DUTY, dailyDutyEventsService);
                            }
                            return 0;
                        }
                ).sum();
    }

    private int processDate(InfiniteIterator<Employee> empIt, CalendarEvents dailyDutyEvents, CalendarEvents holidayEvents, LocalDate currentDate,
                          EventType dailyDuty, CalendarEventsService dailyDutyEventsService) {
        Employee employee = findNextForDailyDuty(empIt, holidayEvents, currentDate);
        List<CalendarEvent> events = dailyDutyEvents.get(currentDate, dailyDuty);
        if (!isEqualEvent(employee, events)) {
            if (!events.isEmpty()) {
                deleteOutdatedEvents(dailyDutyEvents, currentDate, dailyDutyEventsService);
            }
            createEvent(dailyDutyEventsService, currentDate, employee);
            return 1;
        }
        return 0;
    }

    private void createEvent(CalendarEventsService eventsService, LocalDate currentDate, Employee employee) {
        CalendarEvent event = new CalendarEvent();
        event.setDate(currentDate);
        event.setType(EventType.DAILY_DUTY);
        event.setSubject(employee.getLogin());
        event.getAttendees().add(employee);
        eventsService.create(event);
        LOGGER.debug("Event created: {}", event);
    }

    private String readLastOnDuty(LocalDate startDate, CalendarEventsService dailyDutyEventsService) {
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

    private void deleteOutdatedEvents(CalendarEvents events, LocalDate date, CalendarEventsService dailyDutyEventsService) {
        if (events.contains(date, EventType.DAILY_DUTY)) {
            List<CalendarEvent> deprecated = events.get(date, EventType.DAILY_DUTY);
            for (CalendarEvent d : deprecated) {
                dailyDutyEventsService.delete(d.getId());
            }
        }
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

}
