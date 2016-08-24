package ru.svalov.ma.genplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.Test;
import ru.svalov.ma.genplan.model.ProjectCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class QuickStartTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RestTemplate restTemplate;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Autowired
    private Properties testProperties;

    @Test(enabled = false)
    public void testInvokeTrello() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                testProperties.getProperty("gp.board.url")
        )
                .queryParam("key", testProperties.getProperty("app.key"))
                .queryParam("token", testProperties.getProperty("app.token"));

        ResponseEntity<List<ProjectCard>> projects = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProjectCard>>() {
                }
        );

        System.out.println(projects);
    }

    @Test(enabled = false)
    public void testAddCard() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                testProperties.getProperty("gp.cards.url")
        )
                .queryParam("key", testProperties.getProperty("app.key"))
                .queryParam("token", testProperties.getProperty("app.token"));

        ProjectCard card = new ProjectCard();
        card.setIdBoard(testProperties.getProperty("gp.board.id"));
        card.setIdList(testProperties.getProperty("gp.list.go.id"));
        card.setDesc("test desc");
        card.setName("test name");
        card.setDue(new Date());

        ProjectCard ret = restTemplate.postForObject(
                builder.build().encode().toUri(),
                card,
                ProjectCard.class
        );

        UriComponentsBuilder builderComments = UriComponentsBuilder.fromHttpUrl(
                testProperties.getProperty("gp.cards.url")
                        + "/"
                        + ret.getId()
                        + "/actions/comments"
        )
                .queryParam("key", testProperties.getProperty("app.key"))
                .queryParam("token", testProperties.getProperty("app.token"));

        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("text", "comment 1");
        String c = restTemplate.postForObject(
                builderComments.build().encode().toUri(),
                requestMap,
                String.class
        );

        System.out.println(ret);
    }

    @Test(enabled = false)
    public void testReadInputData() {

        final URL dataUrl = getClass().getClassLoader().getResource("status_report_data_sample.csv");
        if (dataUrl == null) {
            throw new IllegalStateException();
        }
        try {
            final BufferedReader reader = Files.newBufferedReader(
                    Paths.get(dataUrl.toURI()), Charset.forName("cp1251")
            );

            // for each line count number of @
            // 11 - new item start with comment beginning
            // 1 - comment line
            // 3 - last comment line with new item end

            reader
                    .lines()
                    .skip(5)
                    .map(line -> Arrays.asList(line.split("@")).size())
                    .forEach(System.out::println);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
