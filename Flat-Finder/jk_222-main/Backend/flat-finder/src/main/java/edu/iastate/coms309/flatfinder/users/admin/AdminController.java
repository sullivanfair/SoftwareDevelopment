package edu.iastate.coms309.flatfinder.users.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.findAll();
    }

    @GetMapping("/{userName}")
    public ResponseEntity<Admin> getAdminByUserName(@PathVariable String userName) {
        return adminService.findByUserName(userName).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{userName}/{userPassword}")
    public ResponseEntity<Admin> getAdminByUserNameAndUserPassword(@PathVariable String userName, @PathVariable String userPassword) {
        Admin admin = adminService.findByUserNameAndUserPassword(userName, userPassword);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.save(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable String userName, @RequestBody Admin updatedAdmin) {
        if (!adminService.existsByUserName(userName)) {
            return ResponseEntity.notFound().build();
        }
        updatedAdmin.setUserName(userName);
        return ResponseEntity.ok(adminService.save(updatedAdmin));
    }

    @PutMapping("/{userName}/adminPermissions")
    public ResponseEntity<Admin> updateAdminPermissions(@PathVariable String userName, @RequestBody Admin adminPermissionsBody) {
        return adminService.findByUserName(userName).map(existingAdmin -> {
            existingAdmin.setAdminPermissions(adminPermissionsBody.getAdminPermissions());
            return ResponseEntity.ok(adminService.save(existingAdmin));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String userName) {
        if (!adminService.existsByUserName(userName)) {
            return ResponseEntity.notFound().build();
        }
        adminService.deleteByUserName(userName);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Admin> updateAdminField(String userName, java.util.function.Consumer<Admin> fieldUpdater) {
        return adminService.findByUserName(userName).map(admin -> {
            fieldUpdater.accept(admin);
            adminService.save(admin);
            return ResponseEntity.ok(admin);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}