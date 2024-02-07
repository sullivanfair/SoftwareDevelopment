package edu.iastate.coms309.flatfinder.users.landlord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/landlords")
public class LandlordController {
    @Autowired
    private LandlordService landlordService;

    @GetMapping
    public List<Landlord> getAllLandlords() {
        return landlordService.findAll();
    }

    @GetMapping("/{userName}")
    public ResponseEntity<Landlord> getLandlordByUserName(@PathVariable String userName) {
        return landlordService.findByUserName(userName)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{userName}/{userPassword}")
    public ResponseEntity<Landlord> getLandlordByUserNameAndUserPassword(@PathVariable String userName, @PathVariable String userPassword) {
        return landlordService.findByUserNameAndUserPassword(userName, userPassword)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Landlord> createLandlord(@RequestBody Landlord landlord) {
        Landlord savedLandlord = landlordService.save(landlord);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLandlord);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<Landlord> updateLandlord(@PathVariable String userName, @RequestBody Landlord updatedLandlord) {
        return landlordService.findByUserName(userName)
                .map(landlord -> {
                    updatedLandlord.setUserId(landlord.getUserId());
                    Landlord savedLandlord = landlordService.save(updatedLandlord);
                    return ResponseEntity.ok(savedLandlord);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<Void> deleteLandlord(@PathVariable String userName) {
        if (landlordService.existsByUserName(userName)) {
            landlordService.deleteByUserName(userName);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}