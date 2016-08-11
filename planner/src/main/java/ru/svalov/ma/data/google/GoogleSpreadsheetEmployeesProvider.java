package ru.svalov.ma.data.google;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.util.ServiceException;
import ru.svalov.ma.data.EmployeeProviderException;
import ru.svalov.ma.data.EmployeesProvider;
import ru.svalov.ma.model.Employee;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * get employees records from Google Sheet
 */
public class GoogleSpreadsheetEmployeesProvider implements EmployeesProvider {

    private static final String YES = "Yes";
    private String sheetUrl;

    private GoogleSheetsService sheetsService;

    private static Employee createEmployee(ListEntry entry) {
        Employee employee = new Employee();
        employee.setLogin(getEntryValue(entry, EmployeeFieldTag.LOGIN.getTag()));
        employee.setName(getEntryValue(entry, EmployeeFieldTag.NAME.getTag()));
        employee.setDailyDuty(YES.equals(getEntryValue(entry, EmployeeFieldTag.DAILY_DUTY.getTag())));
        employee.setArchitectDailyDuty(YES.equals(getEntryValue(entry, EmployeeFieldTag.ARCHITECT_DAILY_DUTY.getTag())));
        employee.setEmail(getEntryValue(entry, EmployeeFieldTag.EMAIL.getTag()));
        employee.setGmail(getEntryValue(entry, EmployeeFieldTag.GMAIL.getTag()));
        employee.setPermanent(true);
        return employee;
    }

    private static String getEntryValue(ListEntry entry, String tag) {
        return entry.getCustomElements().getValue(tag);
    }

    public Stream<Employee> get(LocalDate startDate, LocalDate endDate) {
        try {
            SpreadsheetService service = sheetsService.getSheetsService();
            SpreadsheetEntry spreadsheet = service.getEntry(new URL(sheetUrl), SpreadsheetEntry.class);
            return service.getFeed((spreadsheet.getWorksheets().get(0)).getListFeedUrl(), ListFeed.class)
                    .getEntries()
                    .stream()
                    .map(GoogleSpreadsheetEmployeesProvider::createEmployee);
        } catch (IOException | ServiceException e) {
            throw new EmployeeProviderException(e);
        }
    }

    public void setSheetUrl(String sheetUrl) {
        this.sheetUrl = sheetUrl;
    }

    public void setSheetsService(GoogleSheetsService sheetsService) {
        this.sheetsService = sheetsService;
    }

    public enum EmployeeFieldTag {

        LOGIN("login"),
        NAME("name"),
        DAILY_DUTY("dailyduty"),
        ARCHITECT_DAILY_DUTY("architectdd"),
        EMAIL("e-mail"),
        GMAIL("gmail");

        private String tag;

        EmployeeFieldTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }
}
