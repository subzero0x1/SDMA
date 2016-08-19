package ru.svalov.ma.planner.google;

import com.google.api.services.calendar.model.Event;
import ru.svalov.ma.model.CalendarEvent;
import ru.svalov.ma.planner.CalendarEventSummaryService;

import java.time.LocalDate;

public class GoogleCalendarEventFactory {

    private CalendarEventSummaryService summaryService;

    public GoogleCalendarEventFactory(CalendarEventSummaryService summaryService) {
        this.summaryService = summaryService;
    }

    public CalendarEvent create(Event event, LocalDate date) {
        final CalendarEvent calendarEvent = new CalendarEvent();
        calendarEvent.setId(event.getId());
        calendarEvent.setDate(date);
        calendarEvent.setType(summaryService.parseType(event.getSummary()));
        calendarEvent.setSubject(summaryService.parseSubject(event.getSummary()));
        return calendarEvent;
    }
}
