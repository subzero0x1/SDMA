package ru.svalov.ma.planner.tempo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import ru.svalov.ma.data.tempo.PlannedVacation;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VacationServiceImpl implements VacationService {
    private String taskName;
    private TempoService tempoService;

    public VacationServiceImpl(String tempoLaborTaskName, TempoService tempoService) {
        taskName = tempoLaborTaskName;
        this.tempoService = tempoService;
    }

    public List<PlannedVacation> parseTasks(String json) {
        Type type = new TypeToken<ArrayList<PlannedVacation>>() {}.getType();

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (json1, type1, jsonDeserializationContext) -> LocalDate.parse(json1.getAsString())).create();

        return gson.fromJson(json, type);
    }

    public List<PlannedVacation> filterLabors(List<PlannedVacation> plannedTasks) {
        return plannedTasks.stream().filter(task -> task.getPlanItem().getName().equals(taskName)).collect(Collectors.toList());
    }

    @Override
    public List<PlannedVacation> getUserLabors(String userName, LocalDate start, LocalDate end) {
        final Optional<String> userPlannedTasks = tempoService.getUserPlannedTasks(userName, start, end);
        final Optional<List<PlannedVacation>> labors = userPlannedTasks.map(this::parseTasks).map(this::filterLabors);
        return labors.orElse(Collections.emptyList());
    }
}
