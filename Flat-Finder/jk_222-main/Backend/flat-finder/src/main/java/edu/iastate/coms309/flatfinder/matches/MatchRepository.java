package edu.iastate.coms309.flatfinder.matches;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    List<Match> findByTenantId(Integer tenantId);
    List<Match> findByListingId(Integer listingId);
}