package ru.svalov.ma.planner.google;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.svalov.ma.model.CalendarEvent;
import ru.svalov.ma.model.EventType;
import ru.svalov.ma.planner.CalendarEventSummaryService;

import java.time.LocalDate;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static ru.svalov.util.JavaTimeUtils.toDateMilli;

@ContextConfiguration(locations = {"classpath:spring-test-config-google.xml"})
public class GoogleCalendarEventFactoryTestCase extends AbstractTestNGSpringContextTests {

    @Autowired
    private GoogleCalendarEventFactory factory;

    @Autowired
    private CalendarEventSummaryService summaryService;

    @Test
    public void testCreate() {
        final String typeId = EventType.DAILY_DUTY.getTypeId();
        final String login = "IvaIP";
        final String id = UUID.randomUUID().toString();
        final LocalDate date = LocalDate.now();

        final CalendarEvent expected = new CalendarEvent();
        expected.setId(id);
        expected.setType(EventType.DAILY_DUTY);
        expected.setDate(date);
        expected.setSubject(login);

        Event event = new Event();
        event.setId(id);
        event.setSummary(typeId + summaryService.getSummarySeparator() + login);
        DateTime dateTime = new DateTime(toDateMilli(date));
        event.setStart(new EventDateTime().setDateTime(dateTime));

        assertEquals(factory.create(event, date), expected);
    }

    // todo : exception throwing cases
}
