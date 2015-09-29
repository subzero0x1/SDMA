package ru.svalov.ma.model;


import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class CalendarEvents {

    private Map<LocalDate, List<CalendarEvent>> eventsByDate = new HashMap<>();
    private LocalDate maxDate = LocalDate.MIN;

    public CalendarEvents add(CalendarEvent event) {
        Objects.requireNonNull(event, "event");
        final LocalDate date = event.getDate();
        if (!eventsByDate.containsKey(date)) {
            eventsByDate.put(date, new ArrayList<>());
            if (date.isAfter(maxDate)) {
                maxDate = date;
            }
        }
        eventsByDate.get(date).add(event);
        return this;
    }

    public boolean contains(LocalDate date, EventType type) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(type, "type");
        return contains(date, (CalendarEvent event) -> type.equals(event.getType()));
    }

    public boolean contains(final LocalDate date, final EventType type, final String subject) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(subject, "subject");
        return contains(date,
                (CalendarEvent event) ->
                        type.equals(event.getType()) && subject.equalsIgnoreCase(event.getSubject())
        );
    }

    private boolean contains(LocalDate date, Predicate<CalendarEvent> predicate) {
        if (!eventsByDate.containsKey(date)) {
            return false;
        }
        List<CalendarEvent> events = eventsByDate.get(date);
        for (CalendarEvent event : events) {
            if (predicate.test(event)) {
                return true;
            }
        }
        return false;
    }

    public List<CalendarEvent> get(LocalDate date, EventType type) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(type, "type");
        if (!eventsByDate.containsKey(date)) {
            return new ArrayList<>();
        }
        return eventsByDate.get(date);
    }

    public List<CalendarEvent> getLast() {
        final List<CalendarEvent> events = eventsByDate.get(maxDate);
        return events == null ? new ArrayList<>() : events;
    }
}
