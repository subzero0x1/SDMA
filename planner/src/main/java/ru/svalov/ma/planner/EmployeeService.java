package ru.svalov.ma.planner;

import ru.svalov.ma.model.Employee;
import ru.svalov.util.InfiniteIterator;

import java.util.function.Predicate;

public interface EmployeeService {

    /**
     * Returns employee list of predicate type
      * @param condition
     * @return
     */
    InfiniteIterator<Employee> readEmpoyees(Predicate<Employee> condition);
}
