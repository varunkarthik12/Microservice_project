package com.example.employee_services.Service;

import com.example.employee_services.Dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DEPARTMENT-SERVICES")
public interface ApiClient_Dept {

    @GetMapping("api/department/{deptCode}")
    DepartmentDto getDepartment (@PathVariable String deptCode);


}
