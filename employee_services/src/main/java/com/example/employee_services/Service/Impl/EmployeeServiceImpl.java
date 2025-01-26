package com.example.employee_services.Service.Impl;

import com.example.employee_services.Dto.DepartmentDto;
import com.example.employee_services.Dto.EmployeeDto;
import com.example.employee_services.Dto.OrganizationDto;
import com.example.employee_services.Dto.ResponseApiDto;
import com.example.employee_services.Entity.Employee;
import com.example.employee_services.Exception.EmailAlreadyExistsException;
import com.example.employee_services.Exception.ResourceNotFoundException;
import com.example.employee_services.Repository.EmployeeRepository;
import com.example.employee_services.Service.ApiClient_Dept;
import com.example.employee_services.Service.ApiClient_Org;
import com.example.employee_services.Service.EmployeeService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private ModelMapper mod_map;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ApiClient_Dept apiClient_dept;

    @Autowired
    private ApiClient_Org apiClient_org;

    @Override
    public EmployeeDto createEmployee(EmployeeDto empDto) {
        Employee empToSave = mod_map.map(empDto, Employee.class);

        if(empRepo.existsByEmail(empToSave.getEmail()))
            throw new EmailAlreadyExistsException("Email Already exists");

        return mod_map.map(empRepo.save(empToSave),EmployeeDto.class);
    }

    //@CircuitBreaker(name = "${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}" , fallbackMethod = "getDefaultDepartment")
    @Override
    public ResponseApiDto retrieveEmployee(Long Id) {

        LOGGER.info("inside retrieveEmployee() method");

        Employee retrieved_emp = empRepo.findById(Id).orElseThrow(()->new ResourceNotFoundException("Employee","id",Id));
        /*DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/department?deptCode="+retrieved_emp.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();*/

        DepartmentDto departmentDto = apiClient_dept.getDepartment(retrieved_emp.getDepartmentCode());
        OrganizationDto organizationDto = apiClient_org.getOrganizationByCode(retrieved_emp.getOrganizationCode());

        EmployeeDto retrived_empDto = mod_map.map(retrieved_emp, EmployeeDto.class);

        ResponseApiDto responseApiDto = new ResponseApiDto(departmentDto,retrived_empDto,organizationDto);

        return responseApiDto;
    }

    public ResponseApiDto getDefaultDepartment(Long Id,Exception excep)
    {
        LOGGER.info("inside getDefaultDepartment() method");
        Employee retrieved_emp = empRepo.findById(Id).orElseThrow(()->new ResourceNotFoundException("Employee","id",Id));

        DepartmentDto departmentDto = new DepartmentDto(null,"R&D","Research and Development","RD001");

        OrganizationDto organizationDto = new OrganizationDto(null,"null","null","null", LocalDate.now());
        EmployeeDto retrivd_empDto = mod_map.map(retrieved_emp, EmployeeDto.class);

        ResponseApiDto responseApiDto = new ResponseApiDto(departmentDto,retrivd_empDto,organizationDto);

        return responseApiDto;
    }
}
