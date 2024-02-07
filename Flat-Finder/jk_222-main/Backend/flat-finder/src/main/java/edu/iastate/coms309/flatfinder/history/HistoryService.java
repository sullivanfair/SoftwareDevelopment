package edu.iastate.coms309.flatfinder.history;

import edu.iastate.coms309.flatfinder.listing.Listing;
import edu.iastate.coms309.flatfinder.listing.ListingRepository;
import edu.iastate.coms309.flatfinder.users.tenant.Tenant;
import edu.iastate.coms309.flatfinder.users.tenant.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final ListingRepository listingRepository;
    private final TenantRepository tenantRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository, ListingRepository listingRepository, TenantRepository tenantRepository) {
        this.historyRepository = historyRepository;
        this.listingRepository = listingRepository;
        this.tenantRepository = tenantRepository;
    }

    public List<History> getAllHistories() {
        return historyRepository.findAll();
    }

    public List<History> getHistoriesByTenantUserName(String userName) {
        List<History> historiesByTenant = historyRepository.findByTenantUserName(userName);
        List<History> historiesByTenant2 = historyRepository.findByTenant2UserName(userName);

        historiesByTenant.addAll(historiesByTenant2);

        return historiesByTenant;
    }


    public List<History> getHistoriesByListingListingName(String listingName) {
        return historyRepository.findByListingListingName(listingName);
    }

    public History createListingHistory(History history) {
        if (history.getHistoryType() != History.HistoryType.LISTING_AND_TENANT) {
            throw new IllegalArgumentException("Invalid history type for createListingHistory");
        }
        // Set the Listing association
        Listing listing = listingRepository.findById(history.getListing().getListingId()).orElseThrow(() -> new RuntimeException("Listing not found"));
        history.setListing(listing);
        // Set the Tenant association
        Tenant tenant = tenantRepository.findById(history.getTenant().getUserId()).orElseThrow(() -> new RuntimeException("Tenant not found"));
        history.setTenant(tenant);
        // Set Tenant2 association as null (for ListingHistory)
        history.setTenant2(null);
        return historyRepository.save(history);
    }

    public History createTenantHistory(History history) {
        if (history.getHistoryType() != History.HistoryType.TENANT_AND_TENANT) {
            throw new IllegalArgumentException("Invalid history type for createTenantHistory");
        }
        // Set Tenant association
        Tenant tenant = tenantRepository.findById(history.getTenant().getUserId()).orElseThrow(() -> new RuntimeException("Tenant not found"));
        history.setTenant(tenant);
        // Set Tenant2 association
        Tenant tenant2 = tenantRepository.findById(history.getTenant2().getUserId()).orElseThrow(() -> new RuntimeException("Tenant2 not found"));
        history.setTenant2(tenant2);
        // Set Listing association as null (for TenantHistory)
        history.setListing(null);
        return historyRepository.save(history);
    }

    public History updateHistory(Integer historyId, History updatedHistory) {
        Optional<History> existingHistory = historyRepository.findById(historyId);
        if (existingHistory.isPresent()) {
            History historyToUpdate = existingHistory.get();
            historyToUpdate.setDescription(updatedHistory.getDescription());
            return historyRepository.save(historyToUpdate);
        } else {
            throw new RuntimeException("History record with ID " + historyId + " not found.");
        }
    }

    public void deleteHistory(Integer historyId) {
        historyRepository.deleteById(historyId);
    }
}