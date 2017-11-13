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

        // todo : refactor

        int undoneS = 0;
        int undoneM = 0;
        int undoneL = 0;
        int doneS = 0;
        int doneM = 0;
        int doneL = 0;
        for (ProgressReportItem item : re.getBody()) {

            if (board.getDone().getId().equals(item.getIdList())) {
                if (item.getIdLabels().contains(board.getSmall().getId())) {
                    doneS++;
                } else if (item.getIdLabels().contains(board.getMiddle().getId())) {
                    doneM++;
                } else if (item.getIdLabels().contains(board.getLarge().getId())) {
                    doneL++;
                }
            } else if (item.getIdLabels().contains(board.getWeek().getId())) {
                if (item.getIdLabels().contains(board.getSmall().getId())) {
                    undoneS++;
                } else if (item.getIdLabels().contains(board.getMiddle().getId())) {
                    undoneM++;
                } else if (item.getIdLabels().contains(board.getLarge().getId())) {
                    undoneL++;
                }
            }
        }

        // build report text
        StringBuilder builder = new StringBuilder();
        TextUtils.append(builder, "Undone S");
        TextUtils.append(builder, String.valueOf(undoneS));
        TextUtils.append(builder, "Undone M");
        TextUtils.append(builder, String.valueOf(undoneM));
        TextUtils.append(builder, "Undone L");
        TextUtils.append(builder, String.valueOf(undoneL));
        TextUtils.append(builder, "Done S");
        TextUtils.append(builder, String.valueOf(doneS));
        TextUtils.append(builder, "Done M");
        TextUtils.append(builder, String.valueOf(doneM));
        TextUtils.append(builder, "Done L");
        TextUtils.append(builder, String.valueOf(doneL));

        return builder.toString();
    }

}
