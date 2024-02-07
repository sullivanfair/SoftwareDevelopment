package edu.iastate.coms309.flatfinder.users.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Optional<Admin> findByUserName(String userName) {
        return adminRepository.findByUserName(userName);
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    public void deleteByUserName(String userName) {
        Optional<Admin> adminOptional = findByUserName(userName);
        adminOptional.ifPresent(admin -> adminRepository.deleteById(admin.getUserId()));
    }

    public boolean existsByUserName(String userName) {
        return adminRepository.existsByUserName(userName);
    }

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findByUserNameAndUserPassword(String userName, String userPassword) {
        return adminRepository.findByUserNameAndUserPassword(userName, userPassword);
    }
}