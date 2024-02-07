package edu.iastate.coms309.flatfinder.listing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.iastate.coms309.flatfinder.history.History;
import edu.iastate.coms309.flatfinder.users.landlord.Landlord;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer listingId;

    @Column(name = "listing_name")
    private String listingName;

    @Transient
    private Integer listingOwnerId;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private Landlord listingOwner;

    @Column(name = "listing_address")
    private String listingAddress;

    @Column(name = "listing_price")
    private float listingPrice;

    @Column(name = "listing_status")
    private String listingStatus;

    @Column(name = "listing_amenities")
    private String listingAmenities;

    @Column(name = "listing_pet_preference")
    private String listingPetPreference;

    @Column(name = "listing_bedrooms")
    private int listingBedrooms;

    @Column(name = "listing_bathrooms")
    private int listingBathrooms;

    @Column(name = "listing_likes")
    private int listingLikes;

    @JsonIgnore
    @OneToMany(mappedBy = "listing")
    private List<ListingApplication> applications;

    @JsonIgnore
    @OneToMany(mappedBy = "listing")
    private List<History> listingTenantHistories;
}