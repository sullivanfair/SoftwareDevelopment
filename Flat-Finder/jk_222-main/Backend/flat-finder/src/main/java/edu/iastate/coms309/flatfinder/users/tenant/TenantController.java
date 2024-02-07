package edu.iastate.coms309.flatfinder.users.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tenants")
public class TenantController {
    @Autowired
    private TenantService tenantService;

    @GetMapping
    public List<Tenant> getAllTenants() {
        return tenantService.findAll();
    }

    @GetMapping("/{name}/{password}/matches")
    public List<Tenant> getTenantMatches(@PathVariable String name, @PathVariable String password) {
        Tenant tenant = getTenant(name, password);
        return getMatches(tenant);
    }

    private Tenant getTenant(String name, String password) {
        List<Tenant> tenantList = tenantService.findAll();
        return tenantList.stream().filter(tenant -> tenant.getUserName().equals(name) && tenant.getUserPassword().equals(password)).findFirst().orElse(null);
    }

    private List<Tenant> getMatches(Tenant tenant) {
        List<Tenant> tenantList = tenantService.findAll();
        List<Tenant> tenantMatches = new ArrayList<>();
        tenantMatches.add(tenant);
        for (Tenant tenantMatch : tenantList) {
            if (tenantMatch.getUserName().equals(tenant.getUserName()) && tenantMatch.getUserPassword().equals(tenant.getUserPassword())) {
                continue;
            }
            int matchScore = 0;
            if (tenantMatch.getTenantBudget().equals(tenant.getTenantBudget())) {
                matchScore++;
            }
            if (tenantMatch.getTenantPetPreference().equals(tenant.getTenantPetPreference())) {
                matchScore++;
            }
            if (tenantMatch.getTenantBedroomPreference().equals(tenant.getTenantBedroomPreference())) {
                matchScore++;
            }
            if (tenantMatch.getTenantBathroomPreference().equals(tenant.getTenantBathroomPreference())) {
                matchScore++;
            }
            if (matchScore >= 3) {
                tenantMatches.add(tenantMatch);
            }
            if (tenantMatches.size() >= 10) {
                break;
            }
        }
        return tenantMatches;
    }

    @PostMapping
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
        tenantService.save(tenant);
        return ResponseEntity.ok(tenant);
    }

    @PutMapping("/{name}/{password}")
    public Tenant updateTenant(@RequestBody Tenant newTenant, @PathVariable String name, @PathVariable String password) {
        Tenant oldTenant = getTenant(name, password);
        newTenant.setUserId(oldTenant.getUserId());
        return tenantService.save(newTenant);
    }

    @DeleteMapping("/{name}/{password}")
    public ResponseEntity<Void> deleteTenant(@PathVariable String name, @PathVariable String password) {
        Tenant tenant = getTenant(name, password);
        tenantService.deleteById(tenant.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userName}/tenantBudget")
    public ResponseEntity<Tenant> updateTenantBudget(@PathVariable String userName, @RequestBody Tenant tenant) {
        return updateTenantField(userName, existingTenant -> existingTenant.setTenantBudget(tenant.getTenantBudget()));
    }

    @PutMapping("/{userName}/tenantAddress")
    public ResponseEntity<Tenant> updateTenantAddress(@PathVariable String userName, @RequestBody Tenant tenant) {
        return updateTenantField(userName, existingTenant -> existingTenant.setTenantAddress(tenant.getTenantAddress()));
    }

    @PutMapping("/{userName}/tenantPetPreference")
    public ResponseEntity<Tenant> updateTenantPetPreference(@PathVariable String userName, @RequestBody Tenant tenant) {
        return updateTenantField(userName, existingTenant -> existingTenant.setTenantPetPreference(tenant.getTenantPetPreference()));
    }

    @PutMapping("/{userName}/tenantBedroomPreference")
    public ResponseEntity<Tenant> updateTenantBedroomPreference(@PathVariable String userName, @RequestBody Tenant tenant) {
        return updateTenantField(userName, existingTenant -> existingTenant.setTenantBedroomPreference(tenant.getTenantBedroomPreference()));
    }

    @PutMapping("/{userName}/tenantBathroomPreference")
    public ResponseEntity<Tenant> updateTenantBathroomPreference(@PathVariable String userName, @RequestBody Tenant tenant) {
        return updateTenantField(userName, existingTenant -> existingTenant.setTenantBathroomPreference(tenant.getTenantBathroomPreference()));
    }

    private ResponseEntity<Tenant> updateTenantField(String userName, java.util.function.Consumer<Tenant> fieldUpdater) {
        return tenantService.findByUserName(userName).map(tenant -> {
            fieldUpdater.accept(tenant);
            tenantService.save(tenant);
            return ResponseEntity.ok(tenant);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}