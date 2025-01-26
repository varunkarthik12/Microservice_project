package com.example.Organization_services.Service;

import com.example.Organization_services.Dto.OrganizationDto;
import com.example.Organization_services.Entity.Organization;

public interface OrganizationService {

    public OrganizationDto create_organization(OrganizationDto org1);

    public OrganizationDto get_organizationByCode (String org_code);


}
