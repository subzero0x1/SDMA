package ru.svalov.ma.planner;

import java.time.LocalDate;

public interface TeamDailyDutyService {
    int createBoxDevSchedule(LocalDate start, LocalDate end);

    int createArchitectSchedule(LocalDate start, LocalDate end);

    int createRetailSchedule(LocalDate start, LocalDate end);

    int createRetailFrontSchedule(LocalDate start, LocalDate end);
}
