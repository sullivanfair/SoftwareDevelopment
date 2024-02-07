package edu.iastate.coms309.flatfinder.matches;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matchId;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_status")
    private MatchStatus matchStatus;

    // Stores tenant ID when the match involves a specific tenant.
    @Column(name = "tenant_id")
    private Integer tenantId;

    // Stores listing ID when the match involves a specific listing.
    @Column(name = "listing_id")
    private Integer listingId;

    @ElementCollection
    @CollectionTable(name = "tenant_tenant_matches", joinColumns = @JoinColumn(name = "match_id"))
    @MapKeyColumn(name = "matched_tenant_id")
    @Column(name = "match_score")
    private Map<Integer, Integer> tenantTenantMatches; // Maps tenantId to matchScore

    @ElementCollection
    @CollectionTable(name = "listing_tenant_matches", joinColumns = @JoinColumn(name = "match_id"))
    @MapKeyColumn(name = "matched_tenant_id")
    @Column(name = "match_score")
    private Map<Integer, Integer> listingTenantMatches; // Maps tenantId to matchScore

    @ElementCollection
    @CollectionTable(name = "tenant_listing_matches", joinColumns = @JoinColumn(name = "match_id"))
    @MapKeyColumn(name = "matched_listing_id")
    @Column(name = "match_score")
    private Map<Integer, Integer> tenantListingMatches; // Maps listingId to matchScore

    public enum MatchStatus {
        PENDING, ACCEPTED, DECLINED
    }
}