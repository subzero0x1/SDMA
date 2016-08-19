package ru.svalov.ma.data.google;

import com.google.gdata.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.svalov.ProdConfig;
import ru.svalov.ma.model.Employee;

import java.io.IOException;
import java.time.LocalDate;

@ContextConfiguration(classes = ProdConfig.class)
public class GoogleSheetProdTestCase extends AbstractTestNGSpringContextTests {

    @Autowired
    private GoogleSpreadsheetEmployeesProvider employeesProvider;

    private static void printEmployee(Employee employee) {
        System.out.println(employee);
    }

    @Test(enabled = false)
    public void test() throws IOException, ServiceException {
        employeesProvider
                .get()
                .forEach(GoogleSheetProdTestCase::printEmployee);
    }
}