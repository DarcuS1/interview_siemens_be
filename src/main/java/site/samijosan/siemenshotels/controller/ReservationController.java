package site.samijosan.siemenshotels.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.samijosan.siemenshotels.model.Reservation;
import site.samijosan.siemenshotels.service.ReservationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/book")
    public Reservation bookRoom(@RequestBody Reservation reservation) {
        reservation.setCheckIn(LocalDateTime.now().plusDays(1));
        reservation.setCheckOut(reservation.getCheckIn().plusDays(2));
        return reservationService.bookRoom(reservation);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.findAll();
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }

    @PutMapping("/{id}/change-room/{newRoomId}")
    public Reservation changeRoom(@PathVariable Long id, @PathVariable int newRoomId) {
        return reservationService.changeRoom(id, newRoomId);
    }
}