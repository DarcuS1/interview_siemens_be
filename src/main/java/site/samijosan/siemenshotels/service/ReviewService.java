package site.samijosan.siemenshotels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.samijosan.siemenshotels.model.Review;
import site.samijosan.siemenshotels.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsByHotelId(Long hotelId) {
        return reviewRepository.findByHotelId(hotelId);
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }
}

