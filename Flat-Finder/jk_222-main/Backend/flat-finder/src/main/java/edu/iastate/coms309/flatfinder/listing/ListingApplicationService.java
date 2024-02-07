package edu.iastate.coms309.flatfinder.listing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing listing applications.
 */
@Service
public class ListingApplicationService {
    /**
     * The repository used for CRUD operations on listing applications.
     */
    @Autowired
    private ListingApplicationRepository listingApplicationRepository;

    /**
     * Finds a listing application by its ID.
     *
     * @param id the ID of the listing application.
     * @return the listing application if found, otherwise an empty Optional.
     */
    public Optional<ListingApplication> findById(Integer id) {
        return listingApplicationRepository.findById(id);
    }

    /**
     * Retrieves all listing applications.
     *
     * @return a list of all listing applications.
     */
    public List<ListingApplication> findAll() {
        return listingApplicationRepository.findAll();
    }

    /**
     * Saves or updates a listing application.
     *
     * @param listingApplication the listing application to save or update.
     * @return the saved listing application.
     */
    public ListingApplication save(ListingApplication listingApplication) {
        return listingApplicationRepository.save(listingApplication);
    }

    /**
     * Deletes a listing application by its ID.
     *
     * @param id the ID of the listing application to delete.
     */
    public void deleteById(Integer id) {
        listingApplicationRepository.deleteById(id);
    }

    /**
     * Checks if a listing application with the given ID exists.
     *
     * @param id the ID of the listing application to check.
     * @return true if the listing application exists, false otherwise.
     */
    public boolean existsById(Integer id) {
        return listingApplicationRepository.existsById(id);
    }
}