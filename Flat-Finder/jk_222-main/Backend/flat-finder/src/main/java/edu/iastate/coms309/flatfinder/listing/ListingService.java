package edu.iastate.coms309.flatfinder.listing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;

    public Optional<Listing> findById(Integer id) {
        return listingRepository.findById(id);
    }

    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }

    public void deleteById(Integer id) {
        listingRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return listingRepository.existsById(id);
    }

    public Listing findByListingName(String listingName) {
        return listingRepository.findByListingName(listingName);
    }
}