package edu.iastate.coms309.flatfinder.listing;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for CRUD operations on listing applications.
 */
public interface ListingApplicationRepository extends JpaRepository<ListingApplication, Integer> {
}