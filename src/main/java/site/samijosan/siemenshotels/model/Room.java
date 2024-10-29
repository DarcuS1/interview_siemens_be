package site.samijosan.siemenshotels.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "room")
public class Room {
    @Id
    private int roomNumber;
    private int type;
    private double price;

    @JsonProperty("isAvailable")
    private boolean isAvailable;

}
