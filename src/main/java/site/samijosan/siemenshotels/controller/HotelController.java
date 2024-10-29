package site.samijosan.siemenshotels.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.samijosan.siemenshotels.model.Hotel;
import site.samijosan.siemenshotels.service.HotelService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@CrossOrigin(origins = "http://localhost:5173")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.findAll();
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Long id) {
        return hotelService.findById(id);
    }

    @GetMapping("/nearby")
    public List<Hotel> getNearbyHotels(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double radius) {
        return hotelService.findNearbyHotels(latitude, longitude, radius);
    }
}