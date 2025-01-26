package com.example.employee_services.Service;

import com.example.employee_services.Dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8083", value = "Organization-services")
public interface ApiClient_Org {

    @GetMapping("api/Organizations/{org_code}")
    OrganizationDto getOrganizationByCode(@PathVariable String org_code);

}

