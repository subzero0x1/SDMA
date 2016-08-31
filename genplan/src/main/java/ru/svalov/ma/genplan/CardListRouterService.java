package ru.svalov.ma.genplan;


import java.util.Map;

import static org.springframework.util.StringUtils.isEmpty;

public class CardListRouterService {

    private Map<String, String> phases;

    String getListId(String projectPhase) {
        if (isEmpty(projectPhase)) {
            throw new IllegalArgumentException("projectPhase");
        }
        return phases.get(projectPhase);
    }

    public void setPhases(Map<String, String> phases) {
        this.phases = phases;
    }
}