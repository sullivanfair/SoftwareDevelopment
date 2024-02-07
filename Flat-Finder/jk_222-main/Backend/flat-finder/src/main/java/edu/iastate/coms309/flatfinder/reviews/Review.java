package edu.iastate.coms309.flatfinder.reviews;

import edu.iastate.coms309.flatfinder.listing.Listing;
import edu.iastate.coms309.flatfinder.users.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewed_user_id")
    private User reviewedUser;

    @ManyToOne
    @JoinColumn(name = "reviewed_listing_id")
    private Listing reviewedListing;

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private Integer rating;

    public Review() {
    }

    public Review(User reviewer, User reviewedUser, Listing reviewedListing, String content, Integer rating) {
        this.reviewer = reviewer;
        this.reviewedUser = reviewedUser;
        this.reviewedListing = reviewedListing;
        this.content = content;
        this.rating = rating;
    }
}