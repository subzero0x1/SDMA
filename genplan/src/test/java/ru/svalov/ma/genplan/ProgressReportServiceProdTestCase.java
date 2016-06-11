package ru.svalov.ma.genplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.Test;
import ru.svalov.ma.progressreport.ProgressReportService;

import java.util.Properties;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class ProgressReportServiceProdTestCase extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProgressReportService progressReportService;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Autowired
    private Properties testProperties;

    @Test(enabled = false)
    public void testProcess() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                testProperties.getProperty("pr.process.url")
        )
                .queryParam("key", testProperties.getProperty("app.key"))
                .queryParam("token", testProperties.getProperty("app.token"));

        System.out.println(progressReportService.build(builder.build().encode().toUri()));
    }

    @Test(enabled = false)
    public void testDone() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                testProperties.getProperty("pr.done.url")
        )
                .queryParam("key", testProperties.getProperty("app.key"))
                .queryParam("token", testProperties.getProperty("app.token"));

        System.out.println(progressReportService.build(builder.build().encode().toUri()));
    }

}