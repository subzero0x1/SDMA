package ru.svalov.ma.genplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.svalov.ma.genplan.model.ProjectCard;
import ru.svalov.ma.genplan.model.ProjectReport;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

public class ProjectCardFactory {

    // todo : inject ProjectCardNameFactory
    // todo : inject ProjectCardDescriptionFactory
    // todo : create and inject ProjectCardCommentFactory

    @Autowired
    private RestTemplate restTemplate;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Autowired
    private Properties properties;

    public void createOrUpdate(ProjectReport projectReport) {

        // todo : search existing card

        ProjectCard card = new ProjectCard();
        card.setIdBoard(properties.getProperty("gp.board.id"));
        card.setIdList(properties.getProperty("gp.list.go.id"));

        // todo : card.setName();
        // todo : card.setDesc();

        card.setDue(getDueDate(projectReport));

        ProjectCard newCard = postCard(card);

        // todo : add comments to card

    }

    private Date getDueDate(ProjectReport projectReport) {
        LocalDate planedDate = projectReport.getPlanedDate();
        if (planedDate == null) {
            return null;
        }
        return Date.from(planedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private ProjectCard postCard(ProjectCard card) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                properties.getProperty("gp.cards.url")
        )
                .queryParam("key", properties.getProperty("app.key"))
                .queryParam("token", properties.getProperty("app.token"));

        return restTemplate.postForObject(
                builder.build().encode().toUri(),
                card,
                ProjectCard.class
        );
    }

}
