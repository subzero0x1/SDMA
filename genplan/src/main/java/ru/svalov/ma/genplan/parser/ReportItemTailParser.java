package ru.svalov.ma.genplan.parser;

import ru.svalov.ma.genplan.model.ProjectReport;

public class ReportItemTailParser implements ReportItemParser {

    @Override
    public void parse(String[] fields, ProjectReport projectReport) {
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("fields");
        }
        if (projectReport == null) {
            throw new IllegalArgumentException("projectReport");
        }
        if (fields.length != ReportItem.TAIL.getCount()) {
            throw new IllegalStateException("fields length: " + fields.length + ", expected: " + ReportItem.HEAD.getCount());
        }

        projectReport.getActivityStatus().add(fields[Format.ACTIVITY_STATUS.getPosition()]);
        projectReport.setDashboardUrl(fields[Format.DASHBOARD_URL.getPosition()]);
        projectReport.setManagedBy(fields[Format.MANAGED_BY.getPosition()]);
    }

    private enum Format {

        ACTIVITY_STATUS(0),
        DASHBOARD_URL(1),
        MANAGED_BY(2);

        private int position;

        Format(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

}
