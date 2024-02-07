package edu.iastate.coms309.flatfinder.users.user;

import edu.iastate.coms309.flatfinder.chat.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUserNameAndUserPassword(String userName, String userPassword) {
        return userRepository.findByUserNameAndUserPassword(userName, userPassword);
    }

    public void updateExistingUser(User existingUser, User updatedUser) {
        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setUserEmail(updatedUser.getUserEmail());
        existingUser.setUserPassword(updatedUser.getUserPassword());
        existingUser.setAccountStatus(updatedUser.getAccountStatus());
        userRepository.save(existingUser);
    }

    public List<ChatRoom> getChatRooms(User user) {
        return user.getChatRooms();
    }
}