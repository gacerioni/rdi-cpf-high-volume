package io.platformengineer.rdicpfhighvolume.vehicle;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.platformengineer.rdicpfhighvolume.person.Person;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Vehicle")
@Table(name = "vehicle")
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "plate", nullable = false, columnDefinition = "TEXT")
    private String plate;
    @Column(name = "model", nullable = false, columnDefinition = "TEXT")
    private String model;
    @Column(name = "year", nullable = false, columnDefinition = "INTEGER")
    private Integer year;
    @Column(name = "color", nullable = false, columnDefinition = "TEXT")
    private String color;
    @Column(name = "brand", nullable = false, columnDefinition = "TEXT")
    private String brand;
    @Column(name = "latitude", nullable = false, columnDefinition = "TEXT")
    private double latitude;
    @Column(name = "longitude", nullable = false, columnDefinition = "TEXT")
    private double longitude;
    @ManyToOne
    @JoinColumn(
            name = "person_cpf",
            nullable = false,
            referencedColumnName = "cpf",
            foreignKey = @ForeignKey(
                    name = "person_car_fk"
            )
    )
    @JsonBackReference
    private Person person;

    public Vehicle() {
    }

    public Vehicle(String plate, String model, Integer year, String color, String brand, double latitude, double longitude) {
        this.plate = plate;
        this.model = model;
        this.year = year;
        this.color = color;
        this.brand = brand;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
