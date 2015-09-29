package ru.svalov.ma.planner;

import ru.svalov.ma.model.EventType;

import static org.springframework.util.StringUtils.isEmpty;

public class CalendarEventSummaryService {

    private char summarySeparator;

    public String create(String typeId, String subject) {
        return typeId + summarySeparator + subject;
    }

    public EventType parseType(final String summary) {
        checkSummary(summary);
        final String typeId = summary.substring(0, summary.indexOf(summarySeparator));
        for (EventType eventType : EventType.values()) {
            if (eventType.getTypeId().equals(typeId)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("type id '" + typeId + "' not supported");
    }

    public String parseSubject(final String summary) {
        checkSummary(summary);
        return summary.substring(summary.indexOf(summarySeparator) + 1).trim();
    }

    private void checkSummary(String summary) {
        if (isEmpty(summary)) {
            throw new IllegalArgumentException("summary is empty");
        }
        final int indexSep = summary.indexOf(summarySeparator);
        if (indexSep == -1) {
            throw new IllegalArgumentException("summary must contain '" + summarySeparator + "': " + summary);
        }
        if (indexSep == 0) {
            throw new IllegalArgumentException("no type in summary: " + summary);
        }
        if (indexSep + 1 >= summary.length()) {
            throw new IllegalArgumentException("no subject in summary: " + summary);
        }
    }

    public char getSummarySeparator() {
        return summarySeparator;
    }

    public void setSummarySeparator(char summarySeparator) {
        this.summarySeparator = summarySeparator;
    }
}
