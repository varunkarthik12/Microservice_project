package com.example.department_services;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@OpenAPIDefinition(info = @Info(
		title = "Department service REST APIs",
		description = "Depart services rest api for open api documentation.",
		version = "v1.0",
		contact = @Contact(
				name = "varun karthik T",
				email = "varunkarthik201@gmail.com",
				url  ="https://www.abc.com"
		)
))
@SpringBootApplication
@EnableMethodSecurity
public class DepartmentServicesApplication {

	@Bean
	public ModelMapper mod_map()
	{
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(DepartmentServicesApplication.class, args);
	}

}
