package edu.iastate.coms309.flatfinder.listing;

import edu.iastate.coms309.flatfinder.notifications.NotifRepository;
import edu.iastate.coms309.flatfinder.notifications.Notification;
import edu.iastate.coms309.flatfinder.users.tenant.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller for managing listing applications.
 */
@RestController
@RequestMapping("/listingApplications")
public class ListingApplicationController
{
    /**
     * The service layer for listing applications.
     */
    @Autowired
    private ListingApplicationService listingApplicationService;

    @Autowired
    private ListingService listingService;

    private static NotifRepository notifRepo;

    @Autowired
    public void setNotifRepo(NotifRepository repo)
    {
        notifRepo = repo;
    }

    /**
     * Fetch all listing applications.
     *
     * @return list of all listing applications.
     */
    @GetMapping
    public List<ListingApplication> getAllListingApplications()
    {
        return listingApplicationService.findAll();
    }

    /**
     * Fetch a specific listing application by its ID.
     *
     * @param id the ID of the listing application.
     * @return the listing application with the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ListingApplication> getListingApplicationById(@PathVariable Integer id)
    {
        return listingApplicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     *
     * @param id the ID of the landlord whose applications we want to return
     * @return list of applications associated with the landlord
     */
    @GetMapping("/landlords/{id}")
    public List<ListingApplication> getLandlordApplications(@PathVariable int id)
    {
        List<ListingApplication> apps = listingApplicationService.findAll();
        List<Listing> listings = listingService.findAll();

        ArrayList<ListingApplication> landlordApps = new ArrayList<>();

        for(ListingApplication application : apps)
        {
            if(application.getListing() == null)
            {
                continue;
            }
            if(application.getListing().getListingOwner().getUserId() == id)
            {
                landlordApps.add(application);
            }
        }

        return landlordApps;
    }

    /**
     * Create a new listing application.
     *
     * @param listingApplication the listing application to be created.
     * @return the created listing application.
     */
    @PostMapping
    public ResponseEntity<ListingApplication> createListingApplication(@RequestBody ListingApplication listingApplication)
    {
        listingApplicationService.save(listingApplication);
        String landlordEmail = listingApplication.getListing().getListingOwner().getUserEmail();
        notifRepo.save(new Notification(landlordEmail, "You have a new application! [SENT] ", new Date()));
        return ResponseEntity.ok(listingApplication);
    }

    /**
     * Update a listing application.
     *
     * @param id the ID of the listing application to be updated.
     * @param updatedListingApplication the updated details of the listing application.
     * @return the updated listing application.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ListingApplication> updateListingApplication(@PathVariable Integer id, @RequestBody ListingApplication updatedListingApplication)
    {
        if (!listingApplicationService.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }

        updatedListingApplication.setApplicationId(id);
        return ResponseEntity.ok(listingApplicationService.save(updatedListingApplication));
    }

    /**
     * Update the tenant applying for a listing application.
     *
     * @param id the ID of the listing application to be updated.
     * @param applyingTenant the updated tenant applying for the listing.
     * @return the updated listing application.
     */
    @PutMapping("/{id}/applyingTenant")
    public ResponseEntity<ListingApplication> updateApplyingTenant(@PathVariable Integer id, @RequestBody Tenant applyingTenant)
    {
        return updateListingApplicationField(id, application -> application.setApplyingTenant(applyingTenant));
    }

    /**
     * Update the listing for a listing application.
     *
     * @param id the ID of the listing application to be updated.
     * @param listing the updated listing for the application.
     * @return the updated listing application.
     */
    @PutMapping("/{id}/listing")
    public ResponseEntity<ListingApplication> updateListing(@PathVariable Integer id, @RequestBody Listing listing)
    {
        return updateListingApplicationField(id, application -> application.setListing(listing));
    }

    /**
     * Update the status of a listing application.
     *
     * @param id the ID of the listing application to be updated.
     * @param applicationStatus the updated status of the application.
     * @return the updated listing application.
     */
    @PutMapping("/{id}/applicationStatus")
    public ResponseEntity<ListingApplication> updateApplicationStatus(@PathVariable Integer id, @RequestParam String applicationStatus)
    {
        return updateListingApplicationField(id, application -> application.setApplicationStatus(applicationStatus));
    }

    /**
     * Update the application date of a listing application.
     *
     * @param id the ID of the listing application to be updated.
     * @param applicationDate the updated application date.
     * @return the updated listing application.
     */
    @PutMapping("/{id}/applicationDate")
    public ResponseEntity<ListingApplication> updateApplicationDate(@PathVariable Integer id, @RequestParam Date applicationDate)
    {
        return updateListingApplicationField(id, application -> application.setApplicationDate(applicationDate));
    }

    /**
     * Update the notes of a listing application.
     *
     * @param id the ID of the listing application to be updated.
     * @param applicationNotes the updated notes.
     * @return the updated listing application.
     */
    @PutMapping("/{id}/applicationNotes")
    public ResponseEntity<ListingApplication> updateApplicationNotes(@PathVariable Integer id, @RequestParam String applicationNotes)
    {
        return updateListingApplicationField(id, application -> application.setApplicationNotes(applicationNotes));
    }

    /**
     * Update the response date of a listing application.
     *
     * @param id the ID of the listing application to be updated.
     * @param responseDate the updated response date.
     * @return the updated listing application.
     */
    @PutMapping("/{id}/responseDate")
    public ResponseEntity<ListingApplication> updateResponseDate(@PathVariable Integer id, @RequestParam Date responseDate)
    {
        return updateListingApplicationField(id, application -> application.setResponseDate(responseDate));
    }

    /**
     * Update the landlord's response for a listing application.
     *
     * @param id the ID of the listing application to be updated.
     * @param landlordResponse the updated landlord's response.
     * @return the updated listing application.
     */
    @PutMapping("/{id}/landlordResponse")
    public ResponseEntity<ListingApplication> updateLandlordResponse(@PathVariable Integer id, @RequestParam String landlordResponse)
    {
        return updateListingApplicationField(id, application -> application.setLandlordResponse(landlordResponse));
    }

    /**
     * Delete a listing application.
     *
     * @param id the ID of the listing application to be deleted.
     * @return HTTP status indicating the result of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListingApplication(@PathVariable Integer id)
    {
        if (!listingApplicationService.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }

        listingApplicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Utility method to update fields of a listing application.
     *
     * @param id the ID of the listing application to be updated.
     * @param fieldUpdater the operation to perform on the listing application.
     * @return the updated listing application.
     */
    private ResponseEntity<ListingApplication> updateListingApplicationField(Integer id, java.util.function.Consumer<ListingApplication> fieldUpdater)
    {
        return listingApplicationService.findById(id)
                .map(application -> {
                    fieldUpdater.accept(application);
                    listingApplicationService.save(application);
                    return ResponseEntity.ok(application);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}