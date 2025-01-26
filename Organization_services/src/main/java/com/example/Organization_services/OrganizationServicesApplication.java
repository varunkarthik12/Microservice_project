package com.example.Organization_services;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrganizationServicesApplication {

	@Bean
	public ModelMapper mod_map()
	{
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(OrganizationServicesApplication.class, args);
	}

}
