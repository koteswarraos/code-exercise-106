package org.swicere.employee;

import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


public class EmployeeServiceTest {

    EmployeeService employeeService = new EmployeeService();

    @Test
    public void getAverageSalaryTest() {
        System.out.println("getAverageSalaryTest");
        Map<Long, Double> averageSalary = employeeService.getAverageSalary(getEmployees());
        assertEquals("50000.0",averageSalary.get(124l).toString());
    }

    @Test
    public void getManagerSalaryLessThanAverageTest() {
        System.out.println("getAverageSalaryTest");
        Map<Long, Double> averageSalary = employeeService.getAverageSalary(getEmployees());
        List<Employee>  employeesList = employeeService.getManagerSalaryLessThanAverage(getEmployees(),averageSalary);
        assertEquals("45000.0",employeesList.stream().filter(e->e.getEmployeeId().equals(124l)).findFirst().get().getSalary().toString());
    }

    @Test
    public void getManagerSalaryGreaterThanAverageTest() {
        Map<Long, Double> averageSalary = employeeService.getAverageSalary(getEmployees());
        List<Employee> employeesList = employeeService.getManagerSalaryGreaterThanAverage(getEmployees(),averageSalary);
        assertEquals("60000.0",employeesList.stream().filter(e->e.getEmployeeId().equals(123l)).findFirst().get().getSalary().toString());
    }

    @Test
    public void getManagerSalaryGreaterThanAverageNoEmployeeTest() {
        Map<Long, Double> averageSalary = new HashMap<>();
        List<Employee> employeesList = employeeService.getManagerSalaryGreaterThanAverage(getEmployees(),averageSalary);
        assertTrue(employeesList.isEmpty());
    }

    @Test
    public void readEmployeeCSVFileTest() {
        List<Employee> employeeList = employeeService.readEmployeeCSVFile("employeeList.csv");
        assertNotNull(employeeList);
        assertEquals(employeeList.size(), 5);
    }


    @Test
    public void reportingLineTest() {
        List<Employee> employeeList = employeeService.readEmployeeCSVFile("employeeList.csv");
        Map<Long, Employee> employeeMap = employeeList.stream().collect(Collectors.toMap(Employee::getEmployeeId, employee->employee));
        int levelCount = employeeService.reportingLine(employeeList.get(1).getEmployeeId(), employeeMap);
        assertEquals(levelCount, 1);

    }
    @Test
    public void reportingLineNoEmployeeTest() {
        List<Employee> employeeList = employeeService.readEmployeeCSVFile("employeeList.csv");
        Map<Long, Employee> employeeMap = employeeList.stream().collect(Collectors.toMap(Employee::getEmployeeId, employee->employee));
        int levelCount = employeeService.reportingLine(12345l, employeeMap);
        assertEquals(levelCount, 0);

    }

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<Employee>();
        Employee emp = new Employee(124l,"Martin","Chekov",45000d,123l);
        employees.add(emp);
          emp = new Employee(123l,"Joe","Doe",60000d,0l);
         employees.add(emp);
         emp = new Employee(125l,"Bob","Ronstad",47000d,123l);
         employees.add(emp);
         emp = new Employee(300l,"Alice","Hasacat",50000d,124l);
         employees.add(emp);
         emp = new Employee(305l,"Brett","Hardleaf",34000d,300l);
         employees.add(emp);
        return employees;
    }

}
