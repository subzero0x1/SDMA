package ru.svalov.ma.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarEvent {

    private String id;

    private EventType type;

    private String subject;

    private LocalDate date;

    private List<Employee> attendees = new ArrayList<>();

    public CalendarEvent() {
    }

    public CalendarEvent(CalendarEvent calendarEvent) {
        id = calendarEvent.getId();
        date = calendarEvent.getDate();
        type = calendarEvent.getType();
        subject = calendarEvent.getSubject();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Employee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Employee> attendees) {
        this.attendees = attendees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CalendarEvent that = (CalendarEvent) o;

        return !(id != null ? !id.equals(that.id) : that.id != null)
                && type == that.type
                && !(subject != null ? !subject.equals(that.subject) : that.subject != null)
                && !(date != null ? !date.equals(that.date) : that.date != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", subject='" + subject + '\'' +
                ", date=" + date +
                '}';
    }
}
