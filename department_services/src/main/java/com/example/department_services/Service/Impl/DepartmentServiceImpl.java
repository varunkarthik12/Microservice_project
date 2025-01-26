package com.example.department_services.Service.Impl;

import com.example.department_services.Dto.DepartmentDto;
import com.example.department_services.Entity.Department;
import com.example.department_services.Repository.DepartmentRepository;
import com.example.department_services.Service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    public DepartmentRepository deptRepo;

    @Autowired
    public ModelMapper mod_map;


    @Override
    public DepartmentDto createDepartment(DepartmentDto deptDto) {
        Department deptToSave = mod_map.map(deptDto,Department.class);
        return mod_map.map(deptRepo.save(deptToSave),DepartmentDto.class);
    }

    @Override
    public DepartmentDto retrieveDepartment(String departmentCode) {
        return mod_map.map(deptRepo.findByDepartmentCode(departmentCode).get(),DepartmentDto.class);
    }
}
