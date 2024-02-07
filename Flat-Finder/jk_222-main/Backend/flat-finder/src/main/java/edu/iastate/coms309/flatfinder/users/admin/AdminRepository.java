package edu.iastate.coms309.flatfinder.users.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUserName(String userName);

    void deleteByUserName(String userName);

    boolean existsByUserName(String userName);

    Admin findByUserNameAndUserPassword(String userName, String userPassword);
}