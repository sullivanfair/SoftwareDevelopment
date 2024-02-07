package edu.iastate.coms309.flatfinder.matches;

import edu.iastate.coms309.flatfinder.listing.Listing;
import edu.iastate.coms309.flatfinder.listing.ListingRepository;
import edu.iastate.coms309.flatfinder.users.tenant.Tenant;
import edu.iastate.coms309.flatfinder.users.tenant.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ListingRepository listingRepository;

    public List<Match> findAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> findMatchById(Integer id) {
        return matchRepository.findById(id);
    }

    public List<Match> findMatchesByTenantId(Integer tenantId) {
        return matchRepository.findByTenantId(tenantId);
    }

    public List<Match> findMatchesByListingId(Integer listingId) {
        return matchRepository.findByListingId(listingId);
    }

    public Match findMatchedListingsForTenant(Integer tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new IllegalArgumentException("Tenant not found"));
        List<Listing> listings = listingRepository.findAll();

        Map<Integer, Integer> matchScores = new HashMap<>();
        for (Listing listing : listings) {
            int score = calculateListingTenantMatchScore(listing, tenant);
            if (score >= 1) {
                matchScores.put(listing.getListingId(), score);
            }
        }

        Match match = new Match();
        match.setTenantId(tenantId); // Set tenant ID
        match.setTenantListingMatches(matchScores);
        match.setMatchStatus(Match.MatchStatus.PENDING);
        return matchRepository.save(match);
    }

    public Match findMatchedTenantsForListing(Integer listingId) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        List<Tenant> tenants = tenantRepository.findAll();

        Map<Integer, Integer> matchScores = new HashMap<>();
        for (Tenant tenant : tenants) {
            int score = calculateListingTenantMatchScore(listing, tenant);
            if (score >= 1) {
                matchScores.put(tenant.getUserId(), score);
            }
        }

        Match match = new Match();
        match.setListingId(listingId); // Set listing ID
        match.setListingTenantMatches(matchScores);
        match.setMatchStatus(Match.MatchStatus.PENDING);
        return matchRepository.save(match);
    }

    public Match findMatchedTenantsForTenant(Integer tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new IllegalArgumentException("Tenant not found"));
        List<Tenant> tenants = tenantRepository.findAll();

        Map<Integer, Integer> matchScores = new HashMap<>();
        for (Tenant otherTenant : tenants) {
            if (!otherTenant.getUserId().equals(tenantId)) {
                int score = calculateTenantTenantMatchScore(tenant, otherTenant);
                if (score >= 1) {
                    matchScores.put(otherTenant.getUserId(), score);
                }
            }
        }

        Match match = new Match();
        match.setTenantId(tenantId); // Set tenant ID
        match.setTenantTenantMatches(matchScores);
        match.setMatchStatus(Match.MatchStatus.PENDING);
        return matchRepository.save(match);
    }

    public void deleteMatchById(Integer matchId) {
        matchRepository.deleteById(matchId);
    }

    public Match updateMatchStatus(Integer matchId, Match.MatchStatus newStatus) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));

        match.setMatchStatus(newStatus);
        return matchRepository.save(match);
    }

    private int calculateListingTenantMatchScore(Listing listing, Tenant tenant) {
        int score = 0;
        if (listing.getListingAddress().equals(tenant.getTenantAddress())) score++;
        if (listing.getListingPrice() <= Float.parseFloat(tenant.getTenantBudget())) score++;
        if (listing.getListingPetPreference().equals(tenant.getTenantPetPreference())) score++;
        if (listing.getListingBedrooms() >= Integer.parseInt(tenant.getTenantBedroomPreference())) score++;
        if (listing.getListingBathrooms() >= Integer.parseInt(tenant.getTenantBathroomPreference())) score++;
        return score;
    }

    private int calculateTenantTenantMatchScore(Tenant tenant1, Tenant tenant2) {
        int score = 0;
        if (tenant1.getTenantBudget().equals(tenant2.getTenantBudget())) score++;
        if (tenant1.getTenantAddress().equals(tenant2.getTenantAddress())) score++;
        if (tenant1.getTenantPetPreference().equals(tenant2.getTenantPetPreference())) score++;
        if (tenant1.getTenantBedroomPreference().equals(tenant2.getTenantBedroomPreference())) score++;
        if (tenant1.getTenantBathroomPreference().equals(tenant2.getTenantBathroomPreference())) score++;
        return score;
    }
}