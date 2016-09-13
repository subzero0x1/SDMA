package ru.svalov.ma.data.tempo;

import java.time.LocalDate;

public class PlannedVacation {
    private Long id;
    private Assignee assignee;
    private PlanItem planItem;
    private LocalDate start;
    private LocalDate end;

    public Long getId() {
        return id;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public PlanItem getPlanItem() {
        return planItem;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public class Assignee {
        private String key;

        public Assignee(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public class PlanItem {
        private String name;

        public PlanItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
