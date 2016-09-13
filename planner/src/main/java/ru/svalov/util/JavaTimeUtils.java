package ru.svalov.util;


import java.time.*;
import java.util.Objects;

public class JavaTimeUtils {

    private static ZoneId zoneId = ZoneId.systemDefault();

    private JavaTimeUtils() {
    }

    // todo : unit test
    public static LocalDate LocalDateFromMilli(long value) {
        final Instant instant = Instant.ofEpochMilli(value);
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }

    public static long toDateMilli(LocalDate localDate) {
        return toDateMilli(localDate, zoneId);
    }

    public static long toDateMilli(LocalDate localDate, ZoneId zoneId) {
        Objects.requireNonNull(localDate, "localDate");
        Objects.requireNonNull(zoneId, "zoneId");
        return ZonedDateTime
                .of(
                        localDate,
                        LocalTime.of(0, 0, 0, 0),
                        zoneId
                )
                .toInstant()
                .toEpochMilli();
    }

    public static boolean isInPeriod(LocalDate subject, LocalDate start, LocalDate end) {
        return (subject.isAfter(start) || subject.isEqual(start)) && (subject.isBefore(end) || subject.isEqual(end));
    }
}