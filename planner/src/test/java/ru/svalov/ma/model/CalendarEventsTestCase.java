package ru.svalov.ma.model;

import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class CalendarEventsTestCase {

    private static CalendarEvent createCalendarEvent(LocalDate date) {
        CalendarEvent event = new CalendarEvent();
        event.setDate(date);
        event.setType(EventType.DAILY_DUTY);
        return event;
    }

    @Test
    public void testGetLast() {
        LocalDate date1 = LocalDate.of(2015, 8, 3);
        LocalDate date2 = LocalDate.of(2015, 8, 10);
        LocalDate date3 = LocalDate.of(2015, 8, 17);

        CalendarEvents calendarEvents = new CalendarEvents()
                .add(createCalendarEvent(date1))
                .add(createCalendarEvent(date2))
                .add(createCalendarEvent(date3));

        assertNotNull(calendarEvents.getLast());
        assertEquals(1, calendarEvents.getLast().size());
        assertEquals(date3, calendarEvents.getLast().get(0).getDate());
    }

}
