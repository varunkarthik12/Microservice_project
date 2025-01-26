package com.example.employee_services.Controller;

import com.example.employee_services.Dto.EmployeeDto;
import com.example.employee_services.Dto.ResponseApiDto;
import com.example.employee_services.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    @Value("${spring.boot.message}")
    private String check_message;
    @Autowired
    public EmployeeService empServ;


    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee (@RequestBody EmployeeDto empDto)
    {
        return new ResponseEntity<>(empServ.createEmployee(empDto), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseApiDto> getEmployee(@PathVariable Long id)
    {
        return new ResponseEntity<>(empServ.retrieveEmployee(id),HttpStatus.OK);
    }

    @GetMapping("/message")
    public String  get_message()
    {
        return check_message;
    }

}
