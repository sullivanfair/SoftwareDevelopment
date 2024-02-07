package edu.iastate.coms309.flatfinder.users.tenant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.iastate.coms309.flatfinder.history.History;
import edu.iastate.coms309.flatfinder.reviews.Review;
import edu.iastate.coms309.flatfinder.users.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tenant")
@Getter
@Setter
public class Tenant extends User {
    @Column(name = "tenant_budget")
    private String tenantBudget;

    @Column(name = "tenant_address")
    private String tenantAddress;

    @Column(name = "tenant_pet_preference")
    private String tenantPetPreference;

    @Column(name = "tenant_bedroom_preference")
    private String tenantBedroomPreference;

    @Column(name = "tenant_bathroom_preference")
    private String tenantBathroomPreference;

    @JsonIgnore
    @OneToMany(mappedBy = "reviewer")
    private List<Review> writtenReviews;

    @JsonIgnore
    @OneToMany(mappedBy = "reviewedUser")
    private List<Review> receivedReviews;

    @JsonIgnore
    @OneToMany(mappedBy = "tenant")
    private List<History> tenantHistories;

    @JsonIgnore
    @OneToMany(mappedBy = "tenant")
    private List<History> listingHistories;
}