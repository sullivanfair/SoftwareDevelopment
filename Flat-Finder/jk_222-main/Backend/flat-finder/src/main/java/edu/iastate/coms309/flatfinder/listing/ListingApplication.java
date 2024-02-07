package edu.iastate.coms309.flatfinder.listing;

import edu.iastate.coms309.flatfinder.users.tenant.Tenant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents an application for a property listing in the application.
 */
@Entity
@Getter
@Setter
public class ListingApplication
{
    /**
     * The unique ID of the application.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    /**
     * The tenant submitting the application.
     */
    @ManyToOne
    private Tenant applyingTenant;

    /**
     * The listing for which the application is made.
     */
    @ManyToOne
    private Listing listing;

    /**
     * The status of the application (e.g., pending, accepted, rejected).
     */
    private String applicationStatus;

    /**
     * The date when the application was submitted.
     */
    private Date applicationDate;

    /**
     * Any additional notes or information provided with the application.
     */
    private String applicationNotes;

    /**
     * The date when the landlord responded to the application.
     */
    private Date responseDate;

    /**
     * The response provided by the landlord to the application.
     */
    private String landlordResponse;
}