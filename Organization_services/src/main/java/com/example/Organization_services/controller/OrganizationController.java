package com.example.Organization_services.controller;

import com.example.Organization_services.Dto.OrganizationDto;
import com.example.Organization_services.Service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Organizations")
@AllArgsConstructor
public class OrganizationController {

    private OrganizationService orgServ;


    @PostMapping
    public ResponseEntity<OrganizationDto> createOrganization(@RequestBody OrganizationDto org1)
    {
        OrganizationDto saved_org = orgServ.create_organization(org1);
        return new ResponseEntity<>(saved_org, HttpStatus.CREATED);
    }


    @GetMapping("/{orgCode}")
    public ResponseEntity<OrganizationDto> getOrganizationByCode (@PathVariable String orgCode)
    {
        OrganizationDto got_Org = orgServ.get_organizationByCode(orgCode);
        return new ResponseEntity<>(got_Org,HttpStatus.OK);
    }
}
