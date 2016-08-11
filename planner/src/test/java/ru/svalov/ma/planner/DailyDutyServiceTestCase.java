package ru.svalov.ma.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.svalov.ma.data.EmployeesProvider;
import ru.svalov.ma.model.CalendarEvent;
import ru.svalov.ma.model.CalendarEvents;
import ru.svalov.ma.model.Employee;
import ru.svalov.ma.model.EventType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class DailyDutyServiceTestCase extends AbstractTestNGSpringContextTests {

    private static final int DAYS_COUNT = 13;

    @Autowired
    private DailyDutyService service;

    private static CalendarEvents createHolidayEvents(LocalDate startDate, int count, List<String> logins) {
        CalendarEvents events = new CalendarEvents();

        Stream
                .iterate(startDate, d -> d.plusDays(1))
                .limit(count)
                .forEach(date -> logins.forEach(login -> {
                            CalendarEvent event = new CalendarEvent();
                            event.setId(UUID.randomUUID().toString());
                            event.setDate(date);
                            event.setSubject(login);
                            event.setType(EventType.VACATION);
                            events.add(event);
                        }
                        )
                );

        return events;
    }

    private static CalendarEvents createLaborEvents() {
        CalendarEvents events = new CalendarEvents();

        // short day before public holiday
        CalendarEvent event = new CalendarEvent();
        event.setType(EventType.SHORT);
        event.setDate(LocalDate.of(2015, 6, 11));
        event.setId(UUID.randomUUID().toString());
        events.add(event);

        // public holiday
        event = new CalendarEvent();
        event.setType(EventType.HOLIDAY);
        event.setDate(LocalDate.of(2015, 6, 12));
        event.setId(UUID.randomUUID().toString());
        events.add(event);

        return events;
    }

    private static CalendarEvents createLastOnDutyEvents(LocalDate date, String login) {
        CalendarEvents events = new CalendarEvents();
        events.add(createDailyDutyEvent(date, login));
        return events;
    }

    private static CalendarEvent createDailyDutyEvent(LocalDate date, String login) {
        CalendarEvent event = new CalendarEvent();
        event.setDate(date);
        event.setType(EventType.DAILY_DUTY);
        event.setSubject(login);
        return event;
    }

    private static List<Employee> createEmployees() {
        List<Employee> employees = new ArrayList<>();
        int i = 0;
        for (char c = 'a'; c < 'y'; c++) {
            employees.add(
                    createEmployee(
                            String.format("%1$C%1$c%1$c%1$c%1$c%1$c %2$C%2$c%2$c %3$C%3$c%3$c",
                                    c, c + 1, c + 2),
                            i++)
            );
        }
        return employees;
    }

    private static Employee createEmployee(final String name, final int i) {
        Employee e1 = new Employee();
        e1.setName(name);
        e1.setDailyDuty(true);
        final String[] nameParts = name.split(" ");
        final String login = nameParts[0].substring(0, 3) + nameParts[1].charAt(0) + nameParts[2].charAt(0);
        e1.setLogin(login);
        e1.setEmail(login.toLowerCase() + i + "@acme.com");
        e1.setGmail(login.toLowerCase() + "@gmail.com");
        e1.setPermanent(true);
        return e1;
    }

    @Test
    public void testBuild() {

        final LocalDate startDate = LocalDate.of(2015, 6, 8);
        final LocalDate endDate = startDate.plusDays(DAYS_COUNT);

        final int[] employeesOnHoliday = new int[]{3, 5, 8, 9, 11};

        // expect employees
        final List<Employee> employees = createEmployees();
        employees.get(2).setDailyDuty(false);
        final EmployeesProvider employeesProvider = service.getEmployeesProvider();
        expect(employeesProvider.get(eq(startDate), eq(endDate)))
                .andReturn(employees.stream());
        replay(employeesProvider);

        // expect last employee was on duty
        final CalendarEventsService dailyDutyEventsService = service.getDailyDutyEventsService();
        expect(dailyDutyEventsService.get(
                        eq(startDate.minusDays(service.getHistoryLookupDays())),
                        eq(startDate)
                )
        ).andReturn(createLastOnDutyEvents(startDate, employees.get(5).getLogin()));

        // expect outdated daily duty events
        final int[] currentOnDuty = new int[]{0, 1, 10, 11, 12};
        final LocalDate[] currentDutyDates = new LocalDate[]{
                LocalDate.of(2015, 6, 8),
                LocalDate.of(2015, 6, 9),
                LocalDate.of(2015, 6, 10),
                LocalDate.of(2015, 6, 12),
                LocalDate.of(2015, 6, 13)
        };
        assertEquals(currentOnDuty.length, currentDutyDates.length);
        final CalendarEvents outdatedEvents = new CalendarEvents();
        for (int i = 0; i < currentOnDuty.length; i++) {
            final CalendarEvent outdatedEvent = createDailyDutyEvent(currentDutyDates[i], employees.get(currentOnDuty[i]).getLogin());
            outdatedEvent.setId(UUID.randomUUID().toString());
            outdatedEvents.add(outdatedEvent);
        }
        expect(dailyDutyEventsService.get(eq(startDate), eq(endDate)))
                .andReturn(outdatedEvents);

        final int[] expectedOnDuty = new int[]{6, 7, 12, 13, 14, 15, 16, 17};
        final LocalDate[] expectedDutyDates = new LocalDate[]{
                LocalDate.of(2015, 6, 8),
                LocalDate.of(2015, 6, 9),
                LocalDate.of(2015, 6, 11),
                LocalDate.of(2015, 6, 15),
                LocalDate.of(2015, 6, 16),
                LocalDate.of(2015, 6, 17),
                LocalDate.of(2015, 6, 18),
                LocalDate.of(2015, 6, 19)
        };
        assertEquals(expectedOnDuty.length, expectedDutyDates.length);

        Stream
                .iterate(startDate, d -> d.plusDays(1))
                .limit(DAYS_COUNT)
                .forEach(date -> {
                            final int outdatedIndex = Arrays.binarySearch(currentDutyDates, date);
                            // 10 Jun, 2015 is actual and shouldn't be recreated.
                            if (outdatedIndex > -1 && !date.isEqual(LocalDate.of(2015, 6, 10))) {
                                dailyDutyEventsService.delete(eq(outdatedEvents.get(date, EventType.DAILY_DUTY).get(0).getId()));
                                expectLastCall();
                            }
                            final int expectedIndex = Arrays.binarySearch(expectedDutyDates, date);
                            if (expectedIndex > -1) {
                                dailyDutyEventsService.create(
                                        eq(createDailyDutyEvent(
                                                expectedDutyDates[expectedIndex],
                                                employees.get(expectedOnDuty[expectedIndex]).getLogin()
                                        ))
                                );
                                expectLastCall();
                            }
                        }
                );

        replay(dailyDutyEventsService);

        // expect labor events
        final CalendarEventsService laborEventsService = service.getLaborEventsService();
        expect(laborEventsService.get(eq(startDate), eq(endDate)))
                .andReturn(createLaborEvents());
        replay(laborEventsService);

        // expect holiday events
        List<String> subjects = new ArrayList<>();
        for (int i : employeesOnHoliday) {
            subjects.add(employees.get(i).getLogin());
        }
        final CalendarEventsService holidayEventsService = service.getHolidayEventsService();
        expect(holidayEventsService.get(eq(startDate), eq(endDate)))
                .andReturn(createHolidayEvents(startDate, DAYS_COUNT, subjects));
        replay(holidayEventsService);

        service.schedule(startDate, endDate);

        verify(employeesProvider);
        verify(dailyDutyEventsService);
        verify(laborEventsService);
        verify(holidayEventsService);
    }

}
