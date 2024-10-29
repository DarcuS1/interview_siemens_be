package site.samijosan.siemenshotels;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import site.samijosan.siemenshotels.model.Hotel;
import site.samijosan.siemenshotels.repository.HotelRepository;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Hotel>> typeReference = new TypeReference<>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/hotels.json");
        if (inputStream != null) {
            List<Hotel> hotels = mapper.readValue(inputStream, typeReference);
            hotelRepository.saveAll(hotels);
            System.out.println("Hotels loaded successfully.");
        } else {
            System.out.println("File not found!");
        }
    }
}