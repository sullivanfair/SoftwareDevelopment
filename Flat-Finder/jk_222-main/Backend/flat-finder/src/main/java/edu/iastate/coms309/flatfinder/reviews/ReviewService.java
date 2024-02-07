package edu.iastate.coms309.flatfinder.reviews;

import edu.iastate.coms309.flatfinder.listing.Listing;
import edu.iastate.coms309.flatfinder.listing.ListingRepository;
import edu.iastate.coms309.flatfinder.users.user.User;
import edu.iastate.coms309.flatfinder.users.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, ListingRepository listingRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    public Optional<Review> findById(Integer id) {
        return reviewRepository.findById(id);
    }

    public Review save(Review review) {
        // Fetch and set the reviewer
        User reviewer = userRepository.findById(review.getReviewer().getUserId())
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));
        review.setReviewer(reviewer);

        // Fetch and set the reviewed user
        User reviewedUser = userRepository.findById(review.getReviewedUser().getUserId())
                .orElseThrow(() -> new RuntimeException("Reviewed user not found"));
        review.setReviewedUser(reviewedUser);

        // Fetch and set the reviewed listing
        if (review.getReviewedListing() != null && review.getReviewedListing().getListingId() != null) {
            Listing reviewedListing = listingRepository.findById(review.getReviewedListing().getListingId())
                    .orElseThrow(() -> new RuntimeException("Reviewed listing not found"));
            review.setReviewedListing(reviewedListing);
        }

        return reviewRepository.save(review);
    }

    public void deleteById(Integer id) {
        reviewRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return reviewRepository.existsById(id);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
}