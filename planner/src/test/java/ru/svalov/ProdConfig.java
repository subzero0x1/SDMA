package ru.svalov;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.svalov.ma.auth.google.GoogleCredentialService;
import ru.svalov.ma.data.EmployeesProvider;
import ru.svalov.ma.data.google.GoogleSheetsService;
import ru.svalov.ma.data.google.GoogleSpreadsheetEmployeesProvider;
import ru.svalov.ma.planner.*;
import ru.svalov.ma.planner.google.GoogleCalendarEventFactory;
import ru.svalov.ma.planner.google.GoogleCalendarEventsService;
import ru.svalov.ma.planner.google.GoogleCalendarService;
import ru.svalov.ma.planner.tempo.TempoService;
import ru.svalov.ma.planner.tempo.VacationServiceImpl;

import java.util.Arrays;

@Configuration
@PropertySource("classpath:test.properties")
public class ProdConfig {

    @Value("${labor.calendar.id}")
    private String laborCalendarId;
    @Value("${labor.calendar.maxResults}")
    private Integer laborCalendarMaxResults;

    @Value("${holiday.calendar.id}")
    private String holidayCalendarId;
    @Value("${holiday.calendar.maxResults}")
    private Integer holidayCalendarMaxResults;

    @Value("${events.calendar.id}")
    private String dailyDutyCalendarId;
    @Value("${events.calendar.maxResults}")
    private Integer dailyDutyCalendarMaxResults;
    @Value("${dailyduty.reminder.minutes}")
    private Integer dailyDutyReminder;
    @Value("${architect.events.calendar.id}")
    private String architectCalendarId;
    @Value("${architect.events.calendar.maxResults}")
    private Integer architectCalendarMaxResults;
    @Value("${retail.events.calendar.id}")
    private String retailCalendarId;
    @Value("${retail.events.calendar.maxResults}")
    private Integer retailCalendarMaxResults;
    @Value("${retail.front.events.calendar.id}")
    private String retailFrontCalendarId;
    @Value("${retail.front.events.calendar.maxResults}")
    private Integer retailFrontCalendarMaxResults;

    @Value("${tempo.labor.task.name}")
    private String tempoLaborTaskName;
    @Value("${jira.tempo.url}")
    private String jiraTempoURL;
    @Value("${jira.login}")
    private String login;
    @Value("${jira.password}")
    private String password;

    @Bean
    public CalendarEventSummaryService calendarEventSummaryService() {
        return new CalendarEventSummaryService(':');
    }

    @Bean
    public GoogleCalendarEventFactory googleCalendarEventFactory() {
        return new GoogleCalendarEventFactory(calendarEventSummaryService());
    }

    @Bean
    public GoogleSpreadsheetEmployeesProvider googleSpreadsheetEmployeesProvider() {
        return new GoogleSpreadsheetEmployeesProvider(sheetService());
    }

    @Bean
    public GoogleSheetsService sheetService() {
        return new GoogleSheetsService(credentialService());
    }

    @Bean
    public GoogleCredentialService credentialService() {
        return new GoogleCredentialService("offline", "client_secret.json", ".credentials/management-assistant",
                Arrays.asList("https://www.googleapis.com/auth/calendar", "https://spreadsheets.google.com/feeds"));
    }

    @Bean
    public GoogleCalendarService calendarService() {
        return new GoogleCalendarService(credentialService());
    }

    @Bean
    public EmployeeService employeeService() {
        return new EmployeeServiceImpl(employeesProvider());
    }

    @Bean
    public EmployeesProvider employeesProvider() {
        return new GoogleSpreadsheetEmployeesProvider(sheetService());
    }

    @Bean
    public DailyDutyService dailyDutyService() {
        return new DailyDutyService(laborEventsService(), holidayEventService(), vacationReplicationService());
    }

    @Bean
    public CalendarEventsService holidayEventService() {
        return new GoogleCalendarEventsService(holidayCalendarId, holidayCalendarMaxResults, 0, googleCalendarEventFactory(),
                calendarEventSummaryService(), calendarService());
    }

    @Bean
    public GoogleCalendarEventsService laborEventsService() {
        return new GoogleCalendarEventsService(laborCalendarId, laborCalendarMaxResults, 0, googleCalendarEventFactory(),
                calendarEventSummaryService(), calendarService());
    }

    @Bean
    public GoogleCalendarEventsService dailyDutyEventsService() {
        return new GoogleCalendarEventsService(dailyDutyCalendarId, dailyDutyCalendarMaxResults, dailyDutyReminder, googleCalendarEventFactory(),
                calendarEventSummaryService(), calendarService());
    }

    @Bean
    public GoogleCalendarEventsService architectEventsService() {
        return new GoogleCalendarEventsService(architectCalendarId, architectCalendarMaxResults, dailyDutyReminder, googleCalendarEventFactory(),
                calendarEventSummaryService(), calendarService());
    }

    @Bean
    public GoogleCalendarEventsService retailEventsService() {
        return new GoogleCalendarEventsService(retailCalendarId, retailCalendarMaxResults, dailyDutyReminder, googleCalendarEventFactory(),
                calendarEventSummaryService(), calendarService());
    }

    @Bean
    public GoogleCalendarEventsService retailFrontEventsService() {
        return new GoogleCalendarEventsService(retailFrontCalendarId, retailFrontCalendarMaxResults, dailyDutyReminder, googleCalendarEventFactory(),
                calendarEventSummaryService(), calendarService());
    }

    @Bean
    public TeamDailyDutyService teamDailyDutyService() {
        return new TeamDailyDutyServiceImpl(dailyDutyService(), employeeService(), dailyDutyEventsService(), architectEventsService(),
                retailEventsService(), retailFrontEventsService());
    }

    @Bean
    public VacationServiceImpl laborTempoService() {
        return new VacationServiceImpl(tempoLaborTaskName, tempoService());
    }

    @Bean
    public TempoService tempoService() {
        return new TempoService(jiraTempoURL, login, password);
    }

    @Bean
    public VacationReplicationService vacationReplicationService() {
        return new VacationReplicationService(holidayEventService(), laborTempoService());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
