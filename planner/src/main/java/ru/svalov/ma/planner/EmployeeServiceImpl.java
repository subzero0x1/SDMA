package ru.svalov.ma.planner;

import org.springframework.beans.factory.annotation.Autowired;
import ru.svalov.ma.data.EmployeesProvider;
import ru.svalov.ma.model.Employee;
import ru.svalov.util.InfiniteIterator;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {
    private EmployeesProvider employeesProvider;

    @Autowired
    public EmployeeServiceImpl(EmployeesProvider employeesProvider) {
        this.employeesProvider = employeesProvider;
    }


    @Override
    public InfiniteIterator<Employee> readEmpoyees(Predicate<Employee> condition) {
        return new InfiniteIterator<>(employeesProvider
                .get()
                .filter(condition)
                .sorted((e1, e2) -> e1.getLogin().compareTo(e2.getLogin()))
                .collect(Collectors.toList())
        );
    }
}
