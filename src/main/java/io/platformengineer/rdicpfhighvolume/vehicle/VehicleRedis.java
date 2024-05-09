package io.platformengineer.rdicpfhighvolume.vehicle;

import com.redis.om.spring.annotations.GeoIndexed;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.NumericIndexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.data.geo.Point;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class VehicleRedis {
    @NonNull
    private Long id;
    @NonNull
    @Searchable
    private String plate;
    @NonNull
    @Searchable
    private String model;
    @NonNull
    private Integer year;
    @NonNull
    @Indexed(separator = ",")
    private String color;
    @NonNull
    @Indexed
    private String brand;

    private Double latitude;
    private Double longitude;
    @GeoIndexed
    private Point location;
    @NumericIndexed(sortable = true)
    private Double price;


    public Point convertLocationToPoint(String location) {
        String[] parts = location.split(",");
        double longitude = Double.parseDouble(parts[0]);
        double latitude = Double.parseDouble(parts[1]);
        return new Point(longitude, latitude);
    }

    public void setLocation(String location) {
        this.location = convertLocationToPoint(location);
    }

}