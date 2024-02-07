package edu.iastate.coms309.flatfinder.users.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    void deleteByUserName(String userName);

    boolean existsByUserName(String userName);

    User findByUserNameAndUserPassword(String userName, String userPassword);
}