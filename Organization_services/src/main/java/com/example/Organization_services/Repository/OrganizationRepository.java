package com.example.Organization_services.Repository;

import com.example.Organization_services.Entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    Optional<Organization> findByOrganizationCode(String org_code);
}
