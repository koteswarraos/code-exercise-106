package org.swicere.employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeService {


    public List<Employee> readEmployeeCSVFile(String csvFileName) {
        String csvSplitBy = ",";
        Long managerId;

        List<Employee> employees = new ArrayList<>();
        ClassLoader classLoader = EmployeeService.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(csvFileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            StringBuilder content = new StringBuilder();
            int arraySize = reader.readLine().split(csvSplitBy).length;
            String[] employeeData = new String[arraySize];
            String line;

            while ((line = reader.readLine()) != null) {
                employeeData = line.split(csvSplitBy);
                managerId = arraySize == employeeData.length ? Long.parseLong(employeeData[4]) : Long.parseLong("0");
                Employee employee = new Employee(
                        Long.parseLong(employeeData[0]),
                        employeeData[1],
                        employeeData[2],
                        Double.parseDouble(employeeData[3]),
                        managerId
                );
                employees.add(employee);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    public Map<Long, Double> getAverageSalary(List<Employee> employeeList) {
        Map<Long, Double> managerAverageSalaryMap = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getManagerId, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(managerAverageSalaryMap);
        return managerAverageSalaryMap;
    }

    public List<Employee> getManagerSalaryLessThanAverage(List<Employee> employeeList, Map<Long, Double> managerAverageSalaryMap) {
        return employeeList.stream()
                .filter(employee -> managerAverageSalaryMap.containsKey(employee.getEmployeeId()) &&
                        employee.getSalary() < managerAverageSalaryMap.get(employee.getManagerId()))
                .collect(Collectors.toList());
    }

    public List<Employee> getManagerSalaryGreaterThanAverage(List<Employee> employeeList, Map<Long, Double> managerAverageSalaryMap) {
        return employeeList.stream()
                .filter(employee -> managerAverageSalaryMap.containsKey(employee.getEmployeeId()) &&
                        employee.getSalary() > (0.50*managerAverageSalaryMap.get(employee.getManagerId()))).collect(Collectors.toList());

    }
    public static int reportingLine(Long employeeId, Map<Long, Employee> employeeMap) {
        int count = 0;
        while (employeeMap.containsKey(employeeId) && employeeMap.get(employeeId).getManagerId() != 0) {
            count++;
            employeeId = employeeMap.get(employeeId).getManagerId();
        }
        return count;
    }

}
