package site.samijosan.siemenshotels.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "hotel")
public class Hotel {
    @Id
    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

}