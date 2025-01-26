package com.example.employee_services.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseApiDto {

    public DepartmentDto departmentDto;

    public EmployeeDto employeeDto;

    public OrganizationDto organizationDto;
}
