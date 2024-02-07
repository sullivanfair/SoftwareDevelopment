package edu.iastate.coms309.flatfinder.listing;

import edu.iastate.coms309.flatfinder.users.landlord.Landlord;
import edu.iastate.coms309.flatfinder.users.landlord.LandlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/listings")
public class ListingController {
    @Autowired
    private ListingService listingService;

    @Autowired
    private LandlordService landlordService;

    @GetMapping
    public List<Listing> getAllListings() {
        return listingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Integer id) {
        return listingService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/landlords/{id}")
    public List<Listing> getLandlordListings(@PathVariable int id) {
        List<Listing> listings = listingService.findAll();
        ArrayList<Listing> landlordListings = new ArrayList<>();
        for (Listing listing : listings) {
            if (listing.getListingOwner() != null && listing.getListingOwner().getUserId().equals(id)) {
                landlordListings.add(listing);
            }
        }
        return landlordListings;
    }

    @PostMapping
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
        if (listing.getListingOwnerId() != null) {
            Landlord landlord = landlordService.findById(listing.getListingOwnerId())
                    .orElseThrow(() -> new RuntimeException("Landlord not found"));
            listing.setListingOwner(landlord);
        }
        Listing savedListing = listingService.save(listing);
        return ResponseEntity.ok(savedListing);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Integer id, @RequestBody Listing updatedListing) {
        return updateListingField(id, listing -> {
            updatedListing.setListingId(id);
            return listingService.save(updatedListing); // Save the updated listing
        });
    }

    @PutMapping("/{id}/listingName")
    public ResponseEntity<Listing> updateListingName(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String listingName = request.get("listingName");
        return updateListingField(id, listing -> {
            listing.setListingName(listingName);
            return listingService.save(listing); // Save the updated listing
        });
    }

    @PutMapping("/{id}/listingOwner")
    public ResponseEntity<Listing> updateListingOwner(@PathVariable Integer id, @RequestBody Map<String, Integer> request) {
        Integer ownerId = request.get("ownerId");

        return updateListingField(id, listing -> {
            Landlord landlord = landlordService.findById(ownerId)
                    .orElseThrow(() -> new RuntimeException("Landlord not found"));
            listing.setListingOwner(landlord);
            return listingService.save(listing);
        });
    }

    @PutMapping("/{id}/listingAddress")
    public ResponseEntity<Listing> updateListingAddress(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String listingAddress = request.get("listingAddress");
        return updateListingField(id, listing -> {
            listing.setListingAddress(listingAddress);
            return listingService.save(listing); // Save the updated listing
        });
    }

    @PutMapping("/{id}/listingPrice")
    public ResponseEntity<Listing> updateListingPrice(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String listingPriceStr = request.get("listingPrice");

        float listingPrice = Float.parseFloat(listingPriceStr);

        return updateListingField(id, listing -> {
            listing.setListingPrice(listingPrice);
            return listingService.save(listing);
        });
    }

    @PutMapping("/{id}/listingStatus")
    public ResponseEntity<Listing> updateListingStatus(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String listingStatus = request.get("listingStatus");
        return updateListingField(id, listing -> {
            listing.setListingStatus(listingStatus);
            return listingService.save(listing);
        });
    }

    @PutMapping("/{id}/listingAmenities")
    public ResponseEntity<Listing> updateListingAmenities(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String listingAmenities = request.get("listingAmenities");
        return updateListingField(id, listing -> {
            listing.setListingAmenities(listingAmenities);
            return listingService.save(listing);
        });
    }

    @PutMapping("/{id}/listingPetPreference")
    public ResponseEntity<Listing> updateListingPetPreference(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String listingPetPreference = request.get("listingPetPreference");
        return updateListingField(id, listing -> {
            listing.setListingPetPreference(listingPetPreference);
            return listingService.save(listing);
        });
    }

    @PutMapping("/{id}/listingBedrooms")
    public ResponseEntity<Listing> updateListingBedrooms(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String listingBedroomsStr = request.get("listingBedrooms");

        try {
            int listingBedrooms = Integer.parseInt(listingBedroomsStr);
            return updateListingField(id, listing -> {
                listing.setListingBedrooms(listingBedrooms);
                return listingService.save(listing);
            });
        } catch (NumberFormatException e) {
            // Handle the case where the provided bedrooms string is not a valid integer
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/listingBathrooms")
    public ResponseEntity<Listing> updateListingBathrooms(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String listingBathroomsStr = request.get("listingBathrooms");

        try {
            int listingBathrooms = Integer.parseInt(listingBathroomsStr);
            return updateListingField(id, listing -> {
                listing.setListingBathrooms(listingBathrooms);
                return listingService.save(listing);
            });
        } catch (NumberFormatException e) {
            // Handle the case where the provided bathrooms string is not a valid integer
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/incrementLikes")
    public ResponseEntity<Listing> incrementLikes(@PathVariable Integer id) {
        return updateListingField(id, listing -> {
            listing.setListingLikes(listing.getListingLikes() + 1);
            return listingService.save(listing); // Save the updated listing
        });
    }

    @PutMapping("/{id}/decrementLikes")
    public ResponseEntity<Listing> decrementLikes(@PathVariable Integer id) {
        return updateListingField(id, listing -> {
            if (listing.getListingLikes() > 0) {
                listing.setListingLikes(listing.getListingLikes() - 1);
            }
            return listingService.save(listing); // Save the updated listing
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Integer id) {
        if (!listingService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        listingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Listing> updateListingField(Integer id, java.util.function.Function<Listing, Listing> fieldUpdater) {
        return listingService.findById(id).map(listing -> {
            Listing updatedListing = fieldUpdater.apply(listing);
            return ResponseEntity.ok(updatedListing);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}