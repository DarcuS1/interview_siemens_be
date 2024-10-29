package site.samijosan.siemenshotels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.samijosan.siemenshotels.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}