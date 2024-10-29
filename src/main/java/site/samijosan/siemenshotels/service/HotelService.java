package site.samijosan.siemenshotels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.samijosan.siemenshotels.model.Hotel;
import site.samijosan.siemenshotels.repository.HotelRepository;
import site.samijosan.siemenshotels.util.GeoUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public List<Hotel> findNearbyHotels(double userLat, double userLon, double radius) {
        double[] userPosition = GeoUtil.toCartesian(userLat, userLon);
        List<Hotel> allHotels = hotelRepository.findAll();

        return allHotels.stream()
                .filter(hotel -> {
                    double[] hotelPosition = GeoUtil.toCartesian(hotel.getLatitude(), hotel.getLongitude());
                    double distance = GeoUtil.calculateDistance(userPosition, hotelPosition);
                    return distance <= radius * 1000;
                })
                .collect(Collectors.toList());
    }
}