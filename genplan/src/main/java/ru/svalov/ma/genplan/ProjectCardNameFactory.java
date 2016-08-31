package ru.svalov.ma.genplan;


import org.springframework.beans.factory.annotation.Autowired;
import ru.svalov.ma.genplan.model.ProjectReport;

import java.util.Properties;

import static org.springframework.util.StringUtils.isEmpty;

public class ProjectCardNameFactory {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Autowired
    private Properties properties;

    String create(ProjectReport projectReport) {
        if (projectReport == null) {
            throw new IllegalArgumentException("projectReport");
        }
        if (isEmpty(projectReport.getProjectCode())) {
            throw new IllegalArgumentException("projectCode is empty");
        }
        if (isEmpty(projectReport.getSummary())) {
            throw new IllegalArgumentException("summary is empty");
        }

        return projectReport.getProjectCode() + properties.getProperty("gp.card.name.delim") + projectReport.getSummary();
    }
}