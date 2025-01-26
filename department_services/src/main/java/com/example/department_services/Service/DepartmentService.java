package com.example.department_services.Service;

import com.example.department_services.Dto.DepartmentDto;

public interface DepartmentService {

    DepartmentDto  createDepartment(DepartmentDto deptDto);

    DepartmentDto retrieveDepartment(String DepartmentCode);
}
