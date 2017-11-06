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

public class ProgressStatusServiceImpl implements ProgressService {

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

        // group card descriptions by card labels
        Map<String, String> labelsCache = new HashMap<>();
        Map<String, List<String>> items = new HashMap<>();
        for (ProgressReportItem item : re.getBody()) {
            // process card labels
            List<String> idLabels = item.getIdLabels();
            if (idLabels == null || idLabels.size() == 0) {
                throw new IllegalArgumentException("no labels on card '" + item.getDesc() + "'");
            }
            if (idLabels.size() > 1) {
                throw new IllegalArgumentException("too many labels on card '" + item.getDesc() + "'");
            }
            for (String idLabel : idLabels) {
                // add label to cache
                if (!labelsCache.containsKey(idLabel)) {
                    List<ProgressReportLabel> labels = item.getLabels();
                    labels.stream()
                            .filter(label -> idLabel.equals(label.getId()))
                            .forEach(label -> labelsCache.put(label.getId(), label.getName()));
                    if (!labelsCache.containsKey(idLabel)) {
                        throw new IllegalStateException("no label with id '" + idLabel + "' in list of labels");
                    }
                }
                if (!items.containsKey(idLabel)) {
                    items.put(idLabel, new ArrayList<>());
                }
                items.get(idLabel).add(item.getDesc());
            }
        }

        // build report text
        StringBuilder builder = new StringBuilder();
        for (String idLabel : items.keySet()) {
            TextUtils.append(builder, labelsCache.get(idLabel));
            for (String desc : items.get(idLabel)) {
                TextUtils.append(builder, desc);
            }
        }

        return builder.toString();
    }

}
