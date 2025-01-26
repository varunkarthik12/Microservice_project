package com.example.employee_services.Service;

import com.example.employee_services.Dto.EmployeeDto;
import com.example.employee_services.Dto.ResponseApiDto;

public interface EmployeeService {

    EmployeeDto createEmployee (EmployeeDto empDto);


    ResponseApiDto retrieveEmployee(Long Id);
}
