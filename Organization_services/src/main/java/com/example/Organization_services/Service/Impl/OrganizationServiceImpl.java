package com.example.Organization_services.Service.Impl;

import com.example.Organization_services.Dto.OrganizationDto;
import com.example.Organization_services.Entity.Organization;
import com.example.Organization_services.Repository.OrganizationRepository;
import com.example.Organization_services.Service.OrganizationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    public OrganizationRepository orgRep;
    @Autowired
    public ModelMapper mod_map;

    @Override
    public OrganizationDto create_organization(OrganizationDto org1) {
        Organization to_save_org = mod_map.map(org1,Organization.class);
        return mod_map.map(orgRep.save(to_save_org), OrganizationDto.class);
    }

    @Override
    public OrganizationDto get_organizationByCode(String org_code) {
        Organization get_org = orgRep.findByOrganizationCode(org_code).get();
        return mod_map.map(get_org, OrganizationDto.class);
    }
}
