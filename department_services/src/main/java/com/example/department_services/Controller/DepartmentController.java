package com.example.department_services.Controller;

import com.example.department_services.Dto.DepartmentDto;
import com.example.department_services.Service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
        name  = "Department service - DepartmentController",
        description = "Department controller exposes REST APIs for Department-services"
)
@RestController
@RequestMapping("api/department")
public class DepartmentController {

    @Value("${spring.boot.message}")
    private String check_message;

    @Autowired
    private DepartmentService deptServ;

    @Operation(
            summary = "save Department REST API",
            description = "save department REST Api is used to save department object in a database"
    )
    @ApiResponse(
            responseCode = "201 CREATED",
            description = "DB Entry is successful"
    )


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentDto> crerateDepartment(@RequestBody DepartmentDto departmentDto)
    {
        return new ResponseEntity<>(deptServ.createDepartment(departmentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Department REST API",
             description = "get department REST Api is used to get department details from the database"
    )
    @ApiResponse(
            responseCode = "200 OK",
            description = "DB Fetch is successful"
    )

    @GetMapping("/{deptCode}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<DepartmentDto> getDepartment (@PathVariable String deptCode)
    {
        return new ResponseEntity<>(deptServ.retrieveDepartment(deptCode),HttpStatus.OK);
    }


    @GetMapping("/message")
    public String get_message()
    {
        return check_message;
    }

}
