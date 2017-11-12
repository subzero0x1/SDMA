package ru.svalov.ma.progressreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.svalov.ma.progressreport.model.ProgressReportItem;
import ru.svalov.ma.progressreport.model.ProgressReportLabel;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgressStatusServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConfigService configService;

    @Override
    public ProgressResponse build(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("board");
        }

        ResponseEntity<List<ProgressReportItem>> re = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProgressReportItem>>() {
                }
        );


        ProgressResponse response = new ProgressResponse();
        // todo : fill

        return response;
    }

}
