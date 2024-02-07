package edu.iastate.coms309.flatfinder.history;

import edu.iastate.coms309.flatfinder.listing.Listing;
import edu.iastate.coms309.flatfinder.users.tenant.Tenant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "tenant2_id")
    private Tenant tenant2;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @Enumerated(EnumType.STRING)
    @Column(name = "history_type")
    private HistoryType historyType;

    @Column(name = "description")
    private String description;

    public enum HistoryType {
        LISTING_AND_TENANT, TENANT_AND_TENANT
    }
}
