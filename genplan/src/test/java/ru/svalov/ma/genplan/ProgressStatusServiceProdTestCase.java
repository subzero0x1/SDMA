package ru.svalov.ma.genplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.Test;
import ru.svalov.ma.progressreport.ProgressService;
import ru.svalov.ma.progressreport.config.Board;
import ru.svalov.ma.progressreport.config.ConfigService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@ContextConfiguration(locations = {"classpath:spring-prod-config.xml"})
public class ProgressStatusServiceProdTestCase extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProgressService progressStatusReportService;

    @Autowired
    private ConfigService configService;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Autowired
    private Properties properties;

    @Test(enabled = true)
    public void testShared() {

        StringBuilder sb = new StringBuilder();
        ClassLoader classLoader = getClass().getClassLoader();
        try (BufferedReader br = new BufferedReader(new FileReader(classLoader.getResource("test_boards.xml").getFile()))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Board> boards = configService.getBoards(sb.toString());

        for (Board board : boards) {
            System.out.println("---");
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                    board.getUri().toString()
            )
                    .queryParam("key", properties.getProperty("app.key"))
                    .queryParam("token", properties.getProperty("app.token"));

            board.setUri(builder.build().encode().toUri());
            System.out.println(progressStatusReportService.build(board));
        }
    }

}