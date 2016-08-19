package ru.svalov.ma.planner.google;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import org.springframework.beans.factory.annotation.Value;
import ru.svalov.ma.model.CalendarEvent;
import ru.svalov.ma.model.CalendarEvents;
import ru.svalov.ma.planner.CalendarEventSummaryService;
import ru.svalov.ma.planner.CalendarEventsException;
import ru.svalov.ma.planner.CalendarEventsService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.svalov.util.JavaTimeUtils.LocalDateFromMilli;
import static ru.svalov.util.JavaTimeUtils.toDateMilli;


public class GoogleCalendarEventsService implements CalendarEventsService {

    private static final String REMINDER_METHOD_EMAIL = "email";
    private static final String ORDER_BY_START_TIME = "startTime";

    private String calendarId;
    private int maxResults;
    private int reminderMinutes;
    private GoogleCalendarEventFactory eventFactory;
    private CalendarEventSummaryService summaryService;
    private GoogleCalendarService calendarService;

    public GoogleCalendarEventsService(String calendarId, int maxResults, int reminderMinutes, GoogleCalendarEventFactory eventFactory,
                                       CalendarEventSummaryService summaryService, GoogleCalendarService calendarService) {
        this.calendarId = calendarId;
        this.maxResults = maxResults;
        this.reminderMinutes = reminderMinutes;
        this.eventFactory = eventFactory;
        this.summaryService = summaryService;
        this.calendarService = calendarService;
    }

    public CalendarEvents get(LocalDate startDate, LocalDate endDate) {
        try {
            CalendarEvents calendarEvents = new CalendarEvents();
            Events events = calendarService
                    .getCalendarService()
                    .events()
                    .list(calendarId)
                    .setMaxResults(maxResults)
                    .setTimeMin(new DateTime(toDateMilli(startDate)))
                    .setTimeMax(new DateTime(toDateMilli(endDate)))
                    .setOrderBy(ORDER_BY_START_TIME)
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();
            if (items.size() == 0) {
                return calendarEvents;
            }
            for (Event event : items) {
                LocalDate eventStartDate = LocalDateFromMilli(event.getStart().getDate().getValue());
                LocalDate eventEndDate = LocalDateFromMilli(event.getEnd().getDate().getValue());
                CalendarEvent eventPrototype = eventFactory.create(event, startDate);
                for (LocalDate currentDate = eventStartDate; currentDate.isBefore(eventEndDate); currentDate = currentDate.plusDays(1)) {
                    CalendarEvent ce = new CalendarEvent(eventPrototype);
                    ce.setDate(currentDate);
                    calendarEvents.add(ce);
                }
            }
            return calendarEvents;
        } catch (IOException e) {
            throw new CalendarEventsException(e);
        }
    }

    public void create(CalendarEvent calendarEvent) {
        Objects.requireNonNull(calendarEvent, "calendarEvent");
        final String summary = summaryService.create(calendarEvent.getType().getTypeId(), calendarEvent.getSubject());
        Event event = new Event().setSummary(summary);
        event.setStart(new EventDateTime()
                .setDate(new DateTime(calendarEvent.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .setTimeZone(ZoneId.systemDefault().getId()));
        event.setEnd(new EventDateTime()
                .setDate(new DateTime(calendarEvent.getDate().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .setTimeZone(ZoneId.systemDefault().getId()));
        event.setAttendees(
                calendarEvent.getAttendees().stream().map(employee -> new EventAttendee()
                        .setDisplayName(employee.getName())
                        .setEmail(employee.getGmail()))
                        .collect(Collectors.toList())
        );
        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder()
                        .setMethod(REMINDER_METHOD_EMAIL)
                        .setMinutes(reminderMinutes)
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);
        try {
            calendarService
                    .getCalendarService()
                    .events()
                    .insert(calendarId, event)
                    .execute();
        } catch (IOException e) {
            throw new CalendarEventsException(e);
        }
    }

    public void delete(String eventId) {
        Objects.requireNonNull(eventId);
        try {
            calendarService
                    .getCalendarService()
                    .events()
                    .delete(calendarId, eventId)
                    .execute();
        } catch (IOException e) {
            throw new CalendarEventsException(e);
        }
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public void setEventFactory(GoogleCalendarEventFactory eventFactory) {
        this.eventFactory = eventFactory;
    }

    public void setSummaryService(CalendarEventSummaryService summaryService) {
        this.summaryService = summaryService;
    }

    public void setCalendarService(GoogleCalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public void setReminderMinutes(int reminderMinutes) {
        this.reminderMinutes = reminderMinutes;
    }
}
