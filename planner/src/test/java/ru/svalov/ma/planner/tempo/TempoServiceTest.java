package ru.svalov.ma.planner.tempo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.svalov.ProdConfig;

import java.time.LocalDate;
import java.util.Optional;

@ContextConfiguration(classes = ProdConfig.class)
@Test
public class TempoServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    TempoService tempoService;

    @Test
    public void testGetUserPlannedTasks() throws Exception {
        Optional<String> response = tempoService.getUserPlannedTasks("KorMA", LocalDate.of(2016, 8, 1), LocalDate.of(2016, 8, 31));

        assert response.isPresent();
    }
}