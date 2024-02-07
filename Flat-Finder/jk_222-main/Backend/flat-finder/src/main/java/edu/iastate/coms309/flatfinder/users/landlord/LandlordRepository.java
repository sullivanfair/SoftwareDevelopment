package edu.iastate.coms309.flatfinder.users.landlord;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandlordRepository extends JpaRepository<Landlord, Integer> {
    Optional<Landlord> findById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Optional<Landlord> findByUserName(String userName);

    void deleteByUserName(String userName);

    boolean existsByUserName(String userName);

    Optional<Landlord> findByUserNameAndUserPassword(String userName, String userPassword);
}