package edu.iastate.coms309.flatfinder.reviews;

import edu.iastate.coms309.flatfinder.listing.Listing;
import edu.iastate.coms309.flatfinder.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Integer id) {
        return reviewService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review savedReview = reviewService.save(review);
        return ResponseEntity.ok(savedReview);
    }

    @PutMapping("/{id}/content")
    public ResponseEntity<Review> updateContent(@PathVariable Integer id, @RequestBody Review review) {
        return updateReviewField(id, existingReview -> existingReview.setContent(review.getContent()));
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<Review> updateRating(@PathVariable Integer id, @RequestBody Review review) {
        return updateReviewField(id, existingReview -> existingReview.setRating(review.getRating()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        if (!reviewService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Review> updateReviewField(Integer id, java.util.function.Consumer<Review> fieldUpdater) {
        return reviewService.findById(id).map(review -> {
            fieldUpdater.accept(review);
            reviewService.save(review);
            return ResponseEntity.ok(review);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}