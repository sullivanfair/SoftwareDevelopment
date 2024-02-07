package edu.iastate.coms309.flatfinder.users.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName) {
        return userService.findByUserName(userName).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{userName}/{userPassword}")
    public ResponseEntity<User> getUserByUserNameAndUserPassword(@PathVariable String userName, @PathVariable String userPassword) {
        User user = userService.findByUserNameAndUserPassword(userName, userPassword);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody User updatedUser) {
        return userService.findByUserName(userName).map(existingUser -> {
            userService.updateExistingUser(existingUser, updatedUser);
            return ResponseEntity.ok(existingUser);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userName}/userName")
    public ResponseEntity<User> updateUserName(@PathVariable String userName, @RequestBody User user) {
        return updateUserField(userName, existingUser -> existingUser.setUserName(user.getUserName()));
    }

    @PutMapping("/{userName}/userEmail")
    public ResponseEntity<User> updateUserEmail(@PathVariable String userName, @RequestBody User user) {
        return updateUserField(userName, existingUser -> existingUser.setUserEmail(user.getUserEmail()));
    }

    @PutMapping("/{userName}/userPassword")
    public ResponseEntity<User> updateUserPassword(@PathVariable String userName, @RequestBody User user) {
        return updateUserField(userName, existingUser -> existingUser.setUserPassword(user.getUserPassword()));
    }

    @PutMapping("/{userName}/accountStatus")
    public ResponseEntity<User> updateAccountStatus(@PathVariable String userName, @RequestBody User user) {
        return updateUserField(userName, existingUser -> existingUser.setAccountStatus(user.getAccountStatus()));
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userName) {
        if (!userService.existsByUserName(userName)) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteByUserName(userName);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<User> updateUserField(String userName, java.util.function.Consumer<User> fieldUpdater) {
        return userService.findByUserName(userName).map(user -> {
            fieldUpdater.accept(user);
            userService.save(user);
            return ResponseEntity.ok(user);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}