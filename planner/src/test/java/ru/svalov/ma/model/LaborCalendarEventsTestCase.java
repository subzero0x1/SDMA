package ru.svalov.ma.model;

import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

public class LaborCalendarEventsTestCase {

    private static CalendarEvent createCalendarEvent(LocalDate date, EventType type) {
        CalendarEvent event = new CalendarEvent();
        event.setDate(date);
        event.setType(type);
        return event;
    }

    @Test
    public void testIsWeekendOrHoliday() {
        LocalDate date1 = LocalDate.of(2015, 7, 30);
        LocalDate date2 = LocalDate.of(2015, 8, 1);
        LocalDate date3 = LocalDate.of(2015, 8, 3);

        CalendarEvents calendarEvents = new CalendarEvents()
                .add(createCalendarEvent(date1, EventType.HOLIDAY))
                .add(createCalendarEvent(date2, EventType.WORK));

        LaborCalendarEvents laborCalendarEvents = new LaborCalendarEvents(calendarEvents);

        assertEquals(true, laborCalendarEvents.isWeekendOrHoliday(date1));
        assertEquals(false, laborCalendarEvents.isWeekendOrHoliday(date2));
        assertEquals(false, laborCalendarEvents.isWeekendOrHoliday(date3));
    }

}
