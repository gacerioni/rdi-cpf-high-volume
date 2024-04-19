package io.platformengineer.rdicpfhighvolume.vehicle;

import com.redis.om.spring.annotations.GeoIndexed;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class VehicleRedis {
    @NonNull
    private Long id;
    @NonNull
    private String plate;
    @NonNull
    @Searchable
    private String model;
    @NonNull
    private Integer year;
    @NonNull
    private String color;
    @NonNull
    @Indexed
    private String brand;
    private Double latitude;
    private Double longitude;
}