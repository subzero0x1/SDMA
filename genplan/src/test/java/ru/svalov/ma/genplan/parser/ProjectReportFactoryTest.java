package ru.svalov.ma.genplan.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@ContextConfiguration(locations = {"classpath:spring-prod-config.xml"})
public class ProjectReportFactoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProjectReportFactory projectReportFactory;

    @Test
    public void testParseReportLine() {

        final URL dataUrl = getClass().getClassLoader().getResource("status_report_data_sample.csv");
        if (dataUrl == null) {
            throw new IllegalStateException();
        }
        try {
            final BufferedReader reader = Files.newBufferedReader(
                    Paths.get(dataUrl.toURI()), Charset.forName("cp1251")
            );
            reader
                    .lines()
                    .skip(5)
                    .forEach(projectReportFactory::parseReportLine);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
