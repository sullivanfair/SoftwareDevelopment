package edu.iastate.coms309.flatfinder.users.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    public Tenant save(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public List<Tenant> findAll() {
        return tenantRepository.findAll();
    }

    public Optional<Tenant> findById(Integer id) {
        return tenantRepository.findById(id);
    }

    public void deleteById(Integer id) {
        tenantRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return tenantRepository.existsById(id);
    }

    public Optional<Tenant> findByUserName(String userName) {
        return tenantRepository.findByUserName(userName);
    }
}