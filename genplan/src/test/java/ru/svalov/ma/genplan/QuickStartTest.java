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
import ru.svalov.ma.genplan.model.TrelloCard;

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

    @Test(enabled = true)
    public void testInvokeTrello() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                testProperties.getProperty("gp.board.url")
        )
                .queryParam("key", testProperties.getProperty("app.key"))
                .queryParam("token", testProperties.getProperty("app.token"));

        ResponseEntity<List<TrelloCard>> projects = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrelloCard>>() {
                }
        );

        System.out.println(projects);
    }

    @Test(enabled = true)
    public void testAddCard() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                testProperties.getProperty("gp.cards.url")
        )
                .queryParam("key", testProperties.getProperty("app.key"))
                .queryParam("token", testProperties.getProperty("app.token"));

        TrelloCard card = new TrelloCard();
        card.setIdBoard(testProperties.getProperty("gp.board.id"));
        card.setIdList(testProperties.getProperty("gp.list.id"));
        card.setDesc("test desc");
        card.setName("test name");
        card.setDue(new Date());

        TrelloCard ret = restTemplate.postForObject(
                builder.build().encode().toUri(),
                card,
                TrelloCard.class
        );

        UriComponentsBuilder builderComments = UriComponentsBuilder.fromHttpUrl(
                testProperties.getProperty("gp.cards.url")
                        + "/"
                        + card.getId()
                        + "/actions/comments"
        )
                .queryParam("text", "comment 1");
        Object test = restTemplate.exchange(
                builderComments.build().encode().toUri(),
                HttpMethod.POST,
                null,
                Object.class
        );

        System.out.println(ret);
    }

}
