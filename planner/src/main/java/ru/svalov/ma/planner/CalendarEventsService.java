package ru.svalov.ma.planner;

import ru.svalov.ma.model.CalendarEvent;
import ru.svalov.ma.model.CalendarEvents;

import java.time.LocalDate;

public interface CalendarEventsService {

    CalendarEvents get(LocalDate startDate, LocalDate endDate);

    void create(CalendarEvent event);

    void delete(String eventId);

}
