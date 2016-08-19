package ru.svalov.ma.data;

import ru.svalov.ma.model.Employee;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface EmployeesProvider {

    Stream<Employee> get();
}
