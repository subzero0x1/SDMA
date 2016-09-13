package ru.svalov.ma.planner.tempo;

import ru.svalov.ma.data.tempo.PlannedVacation;

import java.time.LocalDate;
import java.util.List;

public interface VacationService {
    List<PlannedVacation> getUserLabors(String userName, LocalDate start, LocalDate end);
}
