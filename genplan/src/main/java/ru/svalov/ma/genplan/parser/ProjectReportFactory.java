package ru.svalov.ma.genplan.parser;

interface ProjectReportFactory {

    static boolean isHead(String[] fields) {
        return fields != null && fields.length == ReportItem.HEAD.getCount();
    }

    static boolean isTail(String[] fields) {
        return fields != null && fields.length == ReportItem.TAIL.getCount();
    }

    void parseReportLine(String line);
}
