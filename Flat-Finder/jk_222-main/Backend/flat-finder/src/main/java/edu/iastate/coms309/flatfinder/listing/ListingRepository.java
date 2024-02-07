package edu.iastate.coms309.flatfinder.listing;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Integer> {
    Listing findByListingName(String listingName);
}
