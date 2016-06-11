package ru.svalov.ma.genplan.parser;

import org.testng.annotations.Test;
import ru.svalov.ma.genplan.model.ProjectReport;

import static org.testng.Assert.assertEquals;

public class ReportItemTailParserTest {

    @Test
    public void testParse() {
        final String[] fields = "3. ���� ����������� ����� �������� ��� ��������� ��� ����� ���������.@https://jira.acme.com/secure/Dashboard.jspa?selectPageId=11223@������ �.�./���������� �.�.".split("@");
        ReportItemTailParser parser = new ReportItemTailParser();
        ProjectReport projectReport = new ProjectReport();

        parser.parse(fields, projectReport);

        assertEquals(projectReport.getActivityStatus().size(), 1);
        assertEquals(projectReport.getActivityStatus().get(0), "3. ���� ����������� ����� �������� ��� ��������� ��� ����� ���������.");
        assertEquals(projectReport.getDashboardUrl(), "https://jira.acme.com/secure/Dashboard.jspa?selectPageId=11223");
        assertEquals(projectReport.getManagedBy(), "������ �.�./���������� �.�.");
    }

}
