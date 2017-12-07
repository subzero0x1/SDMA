package ru.svalov.ma.progressreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.svalov.ma.progressreport.config.Board;
import ru.svalov.ma.progressreport.model.ProgressReportItem;

import java.util.List;

public class ProgressStatusServiceImpl implements ProgressService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String build(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("board");
        }

        ResponseEntity<List<ProgressReportItem>> re = restTemplate.exchange(
                board.getUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProgressReportItem>>() {
                }
        );

        int undoneS = 0, undoneM = 0, undoneL = 0;
        int doneS = 0, doneM = 0, doneL = 0;

        boolean done, week;
        for (ProgressReportItem item : re.getBody()) {
            done = board.getDone().getId().equals(item.getIdList());
            week = item.getIdLabels().contains(board.getWeek().getId());
            if (item.getIdLabels().contains(board.getSmall().getId())) {
                if(done) {
                    doneS++;
                } else if (week) {
                    undoneS++;
                }
            } else if (item.getIdLabels().contains(board.getMiddle().getId())) {
                if(done) {
                    doneM++;
                } else if (week) {
                    undoneM++;
                }
            } else if (item.getIdLabels().contains(board.getLarge().getId())) {
                if(done) {
                    doneL++;
                } else if (week) {
                    undoneL++;
                }
            }
        }

        // build report text
        StringBuilder builder = new StringBuilder();
        TextUtils.append(builder, "-------");
        TextUtils.append(builder, "Undone");
        TextUtils.append(builder, String.format("S: %d, M: %d, L: %d", undoneS, undoneM, undoneL));
        TextUtils.append(builder, "-------");
        TextUtils.append(builder, "Done");
        TextUtils.append(builder, String.format("S: %d, M: %d, L: %d", doneS, doneM, doneL));

        return builder.toString();
    }

}
