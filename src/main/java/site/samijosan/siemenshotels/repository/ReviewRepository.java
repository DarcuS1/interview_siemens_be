package site.samijosan.siemenshotels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.samijosan.siemenshotels.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByHotelId(Long hotelId);
}