package site.samijosan.siemenshotels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.samijosan.siemenshotels.model.Reservation;
import site.samijosan.siemenshotels.repository.ReservationRepository;
import site.samijosan.siemenshotels.model.Hotel;
import site.samijosan.siemenshotels.repository.HotelRepository;
import site.samijosan.siemenshotels.model.Room;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public Reservation bookRoom(Reservation reservation) {
        Reservation savedReservation = reservationRepository.save(reservation);
        updateRoomAvailability(savedReservation.getHotelId(), savedReservation.getRoomNumber(), false);
        return savedReservation;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null) {
            updateRoomAvailability(reservation.getHotelId(), reservation.getRoomNumber(), true);
            reservationRepository.deleteById(reservationId);
        }
    }

    public Reservation changeRoom(Long reservationId, int newRoomId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null && reservation.getCheckIn().isAfter(LocalDateTime.now().plusHours(2))) {
            updateRoomAvailability(reservation.getHotelId(), reservation.getRoomNumber(), true);
            reservation.setRoomNumber(newRoomId);
            Reservation savedReservation = reservationRepository.save(reservation);
            updateRoomAvailability(reservation.getHotelId(), newRoomId, false);
            return savedReservation;
        }
        return reservation;
    }

    private void updateRoomAvailability(Long hotelId, int roomNumber, boolean isAvailable) {
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        if (hotel != null) {
            List<Room> rooms = hotel.getRooms();
            for (Room room : rooms) {
                if (room.getRoomNumber() == roomNumber) {
                    room.setAvailable(isAvailable);
                    break;
                }
            }
            hotelRepository.save(hotel);
        }
    }
}
