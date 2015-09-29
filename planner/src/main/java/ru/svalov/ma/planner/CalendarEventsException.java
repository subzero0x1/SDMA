package ru.svalov.ma.planner;

public class CalendarEventsException extends RuntimeException {
    public CalendarEventsException() {
    }

    public CalendarEventsException(String message) {
        super(message);
    }

    public CalendarEventsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalendarEventsException(Throwable cause) {
        super(cause);
    }

    public CalendarEventsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
