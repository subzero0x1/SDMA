package ru.svalov.ma.genplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.Test;
import ru.svalov.ma.progressreport.ProgressReportService;

import java.util.Properties;

@ContextConfiguration(locations = {"classpath:spring-prod-config.xml"})
public class ProgressReportServiceProdTestCase extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProgressReportService progressReportService;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Autowired
    private Properties properties;

    @Test(enabled = false)
    public void testShared() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                properties.getProperty("pr.shared.url")
        )
                .queryParam("key", properties.getProperty("app.key"))
                .queryParam("token", properties.getProperty("app.token"));

        System.out.println(progressReportService.build(builder.build().encode().toUri()));
    }

    @Test(enabled = false)
    public void testDone() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                properties.getProperty("pr.done.url")
        )
                .queryParam("key", properties.getProperty("app.key"))
                .queryParam("token", properties.getProperty("app.token"));

        System.out.println(progressReportService.build(builder.build().encode().toUri()));
    }

}