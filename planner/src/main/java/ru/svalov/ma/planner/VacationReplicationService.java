package ru.svalov.ma.planner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.svalov.ma.data.tempo.PlannedVacation;
import ru.svalov.ma.model.CalendarEvent;
import ru.svalov.ma.model.CalendarEvents;
import ru.svalov.ma.model.Employee;
import ru.svalov.ma.model.EventType;
import ru.svalov.ma.planner.tempo.VacationService;
import ru.svalov.util.JavaTimeUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

public class VacationReplicationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VacationReplicationService.class);

    private CalendarEventsService holidayEventService;
    private VacationService vacationService;

    public VacationReplicationService(final CalendarEventsService holidayEventService, final VacationService vacationService) {
        this.holidayEventService = holidayEventService;
        this.vacationService = vacationService;
    }

    public void replicateVacations(List<Employee> users, LocalDate start, LocalDate end) {
        List<PlannedVacation> tempoLabors = users.stream().map(Employee::getLogin).map(user -> vacationService.getUserLabors(user, start, end))
                .flatMap(List::stream).collect(Collectors.toList());

        final CalendarEvents calendarEvents = holidayEventService.get(start, end);
        Stream.iterate(start, d -> d.plusDays(1))
                .limit(start.until(end, DAYS))
                .forEach(currentDate -> {
                    final List<CalendarEvent> eventsToRemove = calendarEvents.get(currentDate, EventType.VACATION);
                    eventsToRemove.forEach(ev -> {
                        final long usersCount = users.stream().filter(u -> u.getLogin().equals(ev.getSubject())).count();
                        if (usersCount > 0) holidayEventService.delete(ev.getId());
                    });

                    createNewVacationEvent(users, tempoLabors, currentDate);
                });
    }

    private void createNewVacationEvent(final List<Employee> users, final List<PlannedVacation> tempoVacs, final LocalDate currentDate) {
        tempoVacs.stream().filter(vac -> JavaTimeUtils.isInPeriod(currentDate, vac.getStart(), vac.getEnd())).forEach
                (plannedVacation -> {
                    Optional<Employee> employee = users.stream().filter(user -> user.getLogin().equalsIgnoreCase(plannedVacation.getAssignee().getKey
                            ())).findFirst();

                    if (!employee.isPresent()) {
                        LOGGER.error("Can't find employee with login: {}", plannedVacation.getAssignee().getKey());
                        return;
                    }

                    final CalendarEvent event = new CalendarEvent();
                    event.setDate(currentDate);
                    event.setType(EventType.VACATION);
                    event.setSubject(plannedVacation.getAssignee().getKey());
                    event.setAttendees(Collections.singletonList(employee.get()));
                    holidayEventService.create(event);
                });
    }
}
