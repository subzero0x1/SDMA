package ru.svalov.ma.data;

import ru.svalov.ma.model.Employee;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface EmployeesProvider {

    /**
     * get employees worked in time period
     *
     * @param startDate start date of time period
     * @param endDate   end date of time period
     */
    Stream<Employee> get(LocalDate startDate, LocalDate endDate);
}
