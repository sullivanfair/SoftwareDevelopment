package edu.iastate.coms309.flatfinder.users.landlord;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LandlordService {
    @Autowired
    private LandlordRepository landlordRepository;

    public Landlord save(Landlord landlord) {
        return landlordRepository.save(landlord);
    }

    public List<Landlord> findAll() {
        return landlordRepository.findAll();
    }

    public Optional<Landlord> findById(Integer id) {
        return landlordRepository.findById(id);
    }

    public Optional<Landlord> findByUserName(String userName) {
        return landlordRepository.findByUserName(userName);
    }

    @Transactional
    public void deleteByUserName(String userName) {
        landlordRepository.deleteByUserName(userName);
    }

    public boolean existsByUserName(String userName) {
        return landlordRepository.existsByUserName(userName);
    }

    public Optional<Landlord> findByUserNameAndUserPassword(String userName, String userPassword) {
        return landlordRepository.findByUserNameAndUserPassword(userName, userPassword);
    }
}