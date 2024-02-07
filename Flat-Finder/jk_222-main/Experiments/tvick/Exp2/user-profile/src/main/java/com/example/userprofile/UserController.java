package com.example.userprofile;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AtomicLong counter = new AtomicLong();

    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(counter.incrementAndGet());
        return user;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        // Normally would get this from a database
        return new User(id, "Sample User", "sample@email.com");
    }
}
