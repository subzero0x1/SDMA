package ru.svalov.ma.planner.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;
import ru.svalov.ma.planner.CalendarEventSummaryService;

@ContextConfiguration(locations = {"classpath:spring-test-config-google.xml"})
public class GoogleCalendarEventsServiceTestCase {

    @Autowired
    private GoogleCalendarEventFactory factory;

    @Autowired
    private CalendarEventSummaryService summaryService;

    @Test(enabled = false)
    public void testGet() {
        // todo : implement
    }

    @Test(enabled = false)
    public void testCreate() {
        // todo : implement
    }

    @Test(enabled = false)
    public void testDelete() {
        // todo : implement
    }

}
