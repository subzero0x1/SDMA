package ru.svalov.ma.planner.tempo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.svalov.ProdConfig;
import ru.svalov.ma.data.tempo.PlannedVacation;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@ContextConfiguration(classes = ProdConfig.class)
@Test
public class VacationTempoServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    VacationServiceImpl laborTempoService;

    private String content;

    @BeforeClass
    public void setUp() throws Exception {
        content = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("tempo_vacation_response.json").toURI())));
    }

    @Test
    public void testParseTasks() throws Exception {
        List<PlannedVacation> plannedTasks = laborTempoService.parseTasks(content);
        assert plannedTasks.size() == 2;
        assert plannedTasks.get(1).getAssignee().getKey().equals("KorMA");
        assert plannedTasks.get(1).getPlanItem().getName().equals("6721_BSS_999_CCORNOWORK");
        assert plannedTasks.get(1).getStart().equals(LocalDate.of(2016, 8, 29));
        assert plannedTasks.get(1).getEnd().equals(LocalDate.of(2016, 9, 11));
    }

    @Test
    public void testFilterLabors() throws Exception {
        List<PlannedVacation> plannedTasks = laborTempoService.parseTasks(content);
        List<PlannedVacation> labors = laborTempoService.filterLabors(plannedTasks);

        assert labors.size() == 1;
    }

    @Test
    public void testGetUserLabors() throws Exception {
        final List<PlannedVacation> labors = laborTempoService.getUserLabors("KorMA", LocalDate.of(2016, 8, 1), LocalDate.of(2016, 8, 31));
        assert labors.size() == 1;
        assert labors.get(0).getStart().equals(LocalDate.of(2016, 8, 29));
    }
}