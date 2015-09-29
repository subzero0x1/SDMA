package ru.svalov.util;

import org.testng.annotations.Test;
import ru.svalov.ma.model.Employee;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class InfiniteIteratorTestCase {

    @Test
    public void testNext() {
        List<Employee> employees = new ArrayList<>();
        Employee e1 = new Employee();
        e1.setLogin("john");
        Employee e2 = new Employee();
        e2.setLogin("mike");
        Employee e3 = new Employee();
        e3.setLogin("sara");
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        final InfiniteIterator<Employee> iterator = new InfiniteIterator<>(employees);
        assertEquals(iterator.next(), e1);
        assertEquals(iterator.next(), e2);
        assertEquals(iterator.next(), e3);
        assertEquals(iterator.next(), e1);
        assertEquals(iterator.next(), e2);
    }

    @Test
    public void testNextEmpty() {
        final InfiniteIterator<Employee> iterator = new InfiniteIterator<>(new ArrayList<>());
        assertEquals(iterator.hasNext(), false);
        assertEquals(iterator.next(), null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNextIllegal2() {
        new InfiniteIterator<Employee>(null);
    }
}
