package site.samijosan.siemenshotels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.samijosan.siemenshotels.model.Reservation;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}