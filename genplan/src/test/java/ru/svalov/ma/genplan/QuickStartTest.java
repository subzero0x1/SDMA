package ru.svalov.ma.genplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.Test;
import ru.svalov.ma.genplan.model.Project;

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
                testProperties.getProperty("qst.url")
        )
                .queryParam("key", testProperties.getProperty("app.key"))
                .queryParam("token", testProperties.getProperty("app.token"));

        // fields=name,idList,url&
        ResponseEntity<List<Project>> projects = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Project>>() {
                }
        );

        System.out.println(projects);
    }

    @Test(enabled = false)
    public void testAddCard() {

    }

}
