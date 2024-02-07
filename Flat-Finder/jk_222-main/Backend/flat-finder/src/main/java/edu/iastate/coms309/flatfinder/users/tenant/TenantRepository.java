package edu.iastate.coms309.flatfinder.users.tenant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {
    Optional<Tenant> findByUserName(String userName);
}