package edu.iastate.coms309.flatfinder.users.landlord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.iastate.coms309.flatfinder.listing.Listing;
import edu.iastate.coms309.flatfinder.reviews.Review;
import edu.iastate.coms309.flatfinder.users.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "landlord")
@Getter
@Setter
public class Landlord extends User {
    @JsonIgnore
    @OneToMany(mappedBy = "listingOwner", cascade = CascadeType.ALL)
    private List<Listing> listings;

    @JsonIgnore
    @OneToMany(mappedBy = "reviewedUser")
    private List<Review> receivedReviews;

    @JsonIgnore
    @OneToMany(mappedBy = "reviewer")
    private List<Review> givenReviews;
}