package io.platformengineer.rdicpfhighvolume.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    private String plate;
    private String model;
    private Integer year;
    private String color;
    private String brand;
    private double latitude;
    private double longitude;
}
