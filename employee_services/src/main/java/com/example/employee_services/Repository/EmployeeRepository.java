package com.example.employee_services.Repository;

import com.example.employee_services.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Boolean existsByEmail(String email);

}
