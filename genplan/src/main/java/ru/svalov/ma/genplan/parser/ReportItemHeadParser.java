package ru.svalov.ma.genplan.parser;

import ru.svalov.ma.genplan.model.ProjectReport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ReportItemHeadParser implements ReportItemParser {

    private static final DateTimeFormatter plannedDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void parse(String[] fields, ProjectReport projectReport) {
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("fields");
        }
        if (projectReport == null) {
            throw new IllegalArgumentException("projectReport");
        }
        if (fields.length != ReportItem.HEAD.getCount()) {
            throw new IllegalStateException("fields length: " + fields.length + ", expected: " + ReportItem.HEAD.getCount());
        }

        projectReport.setBankName(fields[Format.BANK_NAME.getPosition()]);
        projectReport.setSummary(fields[Format.SUMMARY.getPosition()]);
        projectReport.setPhase(fields[Format.PHASE.getPosition()]);
        projectReport.setPlanedDate(LocalDate.parse(fields[Format.PLANNED_DATE.getPosition()], plannedDateFormatter));
        projectReport.setProjectCode(fields[Format.PROJECT_CODE.getPosition()]);
        projectReport.getActivityStatus().add(fields[Format.ACTIVITY_STATUS.getPosition()]);
    }

    private enum Format {

        BANK_NAME(1),
        PHASE(4),
        PLANNED_DATE(6),
        PROJECT_CODE(2),
        ACTIVITY_STATUS(10),
        SUMMARY(3);

        private int position;

        Format(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

}
