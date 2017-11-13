package ru.svalov.ma.genplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.Test;
import ru.svalov.ma.progressreport.ProgressService;
import ru.svalov.ma.progressreport.config.Board;

import java.util.Properties;

@ContextConfiguration(locations = {"classpath:spring-prod-config.xml"})
public class ProgressServiceProdTestCase extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProgressService progressReportService;

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

        Board board = new Board();
        board.setUri(builder.build().encode().toUri().toString());
        System.out.println(progressReportService.build(board));
    }

    @Test(enabled = false)
    public void testDone() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                properties.getProperty("pr.done.url")
        )
                .queryParam("key", properties.getProperty("app.key"))
                .queryParam("token", properties.getProperty("app.token"));

        Board board = new Board();
        board.setUri(builder.build().encode().toUri().toString());
        System.out.println(progressReportService.build(board));
    }

}