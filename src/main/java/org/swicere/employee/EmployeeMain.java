package org.swicere.employee;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeMain {




    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        List<Employee> employeeList = employeeService.readEmployeeCSVFile("employeeList.csv");
        Map<Long, Double> managerAverageSalaryMap = employeeService.getAverageSalary(employeeList);
        System.out.println(managerAverageSalaryMap);

        List<Employee> employeeSalaryLessThanAverage = employeeService.getManagerSalaryLessThanAverage(employeeList, managerAverageSalaryMap);
        System.out.println("employee salary less than ehe 20% average salary-------");
        for (Employee employee : employeeSalaryLessThanAverage) {
            System.out.println("Employee ID: " + employee.getEmployeeId() +" Name :"+employee.getFirstName()+ " Salary: " + employee.getSalary());
        }

        List<Employee> employeeSalaryGreaterThanAverage = employeeService.getManagerSalaryGreaterThanAverage(employeeList, managerAverageSalaryMap);
        System.out.println("manager salary greater than the 50 percentage of average salary-------");
        for (Employee employee : employeeSalaryGreaterThanAverage) {
            System.out.println("Employee ID: " + employee.getEmployeeId() +" Name :"+employee.getFirstName()+ " Salary: " + employee.getSalary());
        }

        Map<Long, Employee> employeeMap = employeeList.stream().collect(Collectors.toMap(Employee::getEmployeeId, employee->employee));
        for(Employee employee : employeeList) {
            int levelCount = employeeService.reportingLine(employee.getEmployeeId(), employeeMap);
            System.out.println("Employee ID: " + employee.getEmployeeId() +" Name :"+employee.getFirstName()+ " Reporting Line : " + levelCount);
        }

    }
}
