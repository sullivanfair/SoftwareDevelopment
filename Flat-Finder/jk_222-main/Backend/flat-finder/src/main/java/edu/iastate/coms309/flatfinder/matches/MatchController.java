package edu.iastate.coms309.flatfinder.matches;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    // Get all matches
    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        return ResponseEntity.ok(matchService.findAllMatches());
    }

    // Get match by ID
    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Integer id) {
        Optional<Match> match = matchService.findMatchById(id);
        return match.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get matches by tenant ID
    @GetMapping("/tenantId/{tenantId}")
    public ResponseEntity<List<Match>> getMatchesByTenantId(@PathVariable Integer tenantId) {
        return ResponseEntity.ok(matchService.findMatchesByTenantId(tenantId));
    }

    // Get matches by listing ID
    @GetMapping("/listingId/{listingId}")
    public ResponseEntity<List<Match>> getMatchesByListingId(@PathVariable Integer listingId) {
        return ResponseEntity.ok(matchService.findMatchesByListingId(listingId));
    }

    // Post to find matched listings for a tenant
    @PostMapping("/findListingsForTenant/{tenantId}")
    public ResponseEntity<Match> findMatchedListingsForTenant(@PathVariable Integer tenantId) {
        return ResponseEntity.ok(matchService.findMatchedListingsForTenant(tenantId));
    }

    // Post to find matched tenants for a listing
    @PostMapping("/findTenantsForListing/{listingId}")
    public ResponseEntity<Match> findMatchedTenantsForListing(@PathVariable Integer listingId) {
        return ResponseEntity.ok(matchService.findMatchedTenantsForListing(listingId));
    }

    // Post to find matched tenants for a tenant
    @PostMapping("/findTenantsForTenant/{tenantId}")
    public ResponseEntity<Match> findMatchedTenantsForTenant(@PathVariable Integer tenantId) {
        return ResponseEntity.ok(matchService.findMatchedTenantsForTenant(tenantId));
    }

    // PUT to update match status by ID
    @PutMapping("/{id}/updateStatus")
    public ResponseEntity<Match> updateMatchStatus(
            @PathVariable Integer id,
            @RequestParam Match.MatchStatus newStatus) {
        Match updatedMatch = matchService.updateMatchStatus(id, newStatus);
        return ResponseEntity.ok(updatedMatch);
    }

    // Delete match by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchById(@PathVariable Integer id) {
        matchService.deleteMatchById(id);
        return ResponseEntity.ok().build();
    }
}