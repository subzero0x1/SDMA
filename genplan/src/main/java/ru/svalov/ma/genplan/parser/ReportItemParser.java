package ru.svalov.ma.genplan.parser;

import ru.svalov.ma.genplan.model.ProjectReport;

public interface ReportItemParser {

    void parse(String[] fields, ProjectReport projectReport);

}
