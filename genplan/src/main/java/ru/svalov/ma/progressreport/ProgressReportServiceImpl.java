package ru.svalov.ma.progressreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import ru.svalov.ma.progressreport.model.ProgressReportItem;

import java.net.URI;
import java.util.List;

public class ProgressReportServiceImpl implements ProgressReportService {

    private static final String NL = System.getProperty("line.separator");

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String build(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri");
        }

        ResponseEntity<List<ProgressReportItem>> re = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProgressReportItem>>() {
                }
        );

        StringBuilder builder = new StringBuilder();
        for (ProgressReportItem item : re.getBody()) {
            String desc = item.getDesc();
            if (!StringUtils.isEmpty(desc)) {
                builder.append(desc);
                builder.append(NL);
            }
        }

        return builder.toString();
    }

}