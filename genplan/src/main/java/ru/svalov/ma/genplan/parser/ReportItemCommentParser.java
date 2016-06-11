package ru.svalov.ma.genplan.parser;

import ru.svalov.ma.genplan.model.ProjectReport;

public class ReportItemCommentParser implements ReportItemParser {

    @Override
    public void parse(String[] fields, ProjectReport projectReport) {
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("fields");
        }
        if (projectReport == null) {
            throw new IllegalArgumentException("projectReport");
        }
        if (fields.length != ReportItem.COMMENT.getCount()) {
            throw new IllegalStateException("fields length: " + fields.length + ", expected: " + ReportItem.HEAD.getCount());
        }

        projectReport.getActivityStatus().add(fields[0]);
    }

}
