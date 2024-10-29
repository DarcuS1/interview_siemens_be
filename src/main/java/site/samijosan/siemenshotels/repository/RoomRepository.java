package site.samijosan.siemenshotels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.samijosan.siemenshotels.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}