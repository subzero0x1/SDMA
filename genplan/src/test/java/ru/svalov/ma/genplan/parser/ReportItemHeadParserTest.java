package ru.svalov.ma.genplan.parser;

import org.testng.annotations.Test;
import ru.svalov.ma.genplan.model.ProjectReport;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

public class ReportItemHeadParserTest {

    @Test
    public void testParse() {
        final String[] fields = "1.1.@Название банка@BANK_System_SUBCODE_1122@Название банка. Переход на System при поддержке существующего функционала, доступного через модули CASHFLOW@GO@31.01.16@31.08.2016@@1633@3346@Цель проекта: кастомизация и внедрение SYS на платформе System. Миграция клиентов из SYS".split("@");
        ReportItemHeadParser parser = new ReportItemHeadParser();
        ProjectReport projectReport = new ProjectReport();

        parser.parse(fields, projectReport);

        assertEquals(projectReport.getBankName(), "Название банка");
        assertEquals(projectReport.getSummary(), "Название банка. Переход на System при поддержке существующего функционала, доступного через модули CASHFLOW");
        assertEquals(projectReport.getPhase(), "GO");
        assertEquals(projectReport.getPlanedDate(), LocalDate.of(2016, 8, 31));
        assertEquals(projectReport.getProjectCode(), "BANK_System_SUBCODE_1122");
        assertEquals(projectReport.getActivityStatus().size(), 1);
        assertEquals(projectReport.getActivityStatus().get(0), "Цель проекта: кастомизация и внедрение SYS на платформе System. Миграция клиентов из SYS");
    }
}
