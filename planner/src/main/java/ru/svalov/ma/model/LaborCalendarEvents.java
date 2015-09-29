package ru.svalov.ma.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class LaborCalendarEvents {

    private CalendarEvents calendarEvents;

    public LaborCalendarEvents(CalendarEvents calendarEvents) {
        Objects.requireNonNull(calendarEvents, "calendarEvents");
        this.calendarEvents = calendarEvents;
    }

    public boolean isWeekendOrHoliday(LocalDate date) {
        Objects.requireNonNull(date, "date");
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return ((dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY) &&
                !calendarEvents.contains(date, EventType.WORK)) ||
                calendarEvents.contains(date, EventType.HOLIDAY);
    }
}
