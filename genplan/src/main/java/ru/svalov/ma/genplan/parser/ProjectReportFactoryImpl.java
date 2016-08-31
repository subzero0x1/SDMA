package ru.svalov.ma.genplan.parser;

import org.springframework.beans.factory.annotation.Autowired;
import ru.svalov.ma.genplan.ProjectCardFactory;
import ru.svalov.ma.genplan.model.ProjectReport;

import java.util.Map;
import java.util.Properties;

public class ProjectReportFactoryImpl implements ProjectReportFactory {

    private ProjectCardFactory projectCardFactory;
    private Map<Integer, ReportItemParser> itemParsers;
    private ProjectReport projectReport;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Autowired
    private Properties properties;

    public void parseReportLine(String line) {

        if (line == null || line.length() == 0) {
            throw new IllegalArgumentException("line");
        }

        String[] fields = line.split(properties.getProperty("gp.report.field.delim"));

        if (ProjectReportFactory.isHead(fields)) {
            projectReport = new ProjectReport();
        }

        final int itemType = fields.length;
        if (itemParsers.containsKey(itemType)) {
            itemParsers.get(itemType).parse(fields, projectReport);
        } else {
            throw new IllegalStateException("unsupported item type: " + itemType);
        }

        if (ProjectReportFactory.isTail(fields)) {
            if (projectReport != null) {
                projectCardFactory.createOrUpdate(projectReport);
            }
            projectReport = null;
        }
    }

    public void setItemParsers(Map<Integer, ReportItemParser> itemParsers) {
        this.itemParsers = itemParsers;
    }

    public void setProjectCardFactory(ProjectCardFactory projectCardFactory) {
        this.projectCardFactory = projectCardFactory;
    }
}
