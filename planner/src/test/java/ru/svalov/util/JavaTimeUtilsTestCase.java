package ru.svalov.util;

import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.testng.Assert.assertEquals;

public class JavaTimeUtilsTestCase {

    @Test
    public void testToDateMilli() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        assertEquals(JavaTimeUtils.toDateMilli(LocalDate.now()), calendar.getTimeInMillis());
    }
}