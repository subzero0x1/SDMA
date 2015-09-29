package ru.svalov.ma.planner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.svalov.ma.model.EventType;

import static org.testng.Assert.assertEquals;

public class CalendarEventSummaryServiceTestCase {

    private static final char SEPARATOR = ':';
    private static final String typeId = "VAC";
    private static final String subject = "IvaIP";
    private static final String summary = typeId + SEPARATOR + subject;

    private CalendarEventSummaryService factory;

    @BeforeClass
    public void beforeClass() {
        factory = new CalendarEventSummaryService();
        factory.setSummarySeparator(SEPARATOR);
    }

    @Test
    public void testCreateSummary() {
        assertEquals(factory.create(EventType.VACATION.getTypeId(), subject), summary);
    }

    @Test
    public void testParseTypeId() {
        assertEquals(factory.parseType(summary).getTypeId(), typeId);
    }

    @Test
    public void testParseSubject() {
        assertEquals(factory.parseSubject(summary), subject);
    }

    // todo : exception cases
}
