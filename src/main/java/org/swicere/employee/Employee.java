package org.swicere.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
        Long employeeId;
        String firstName;
        String lastName;
        Double salary;
        Long managerId;
}

