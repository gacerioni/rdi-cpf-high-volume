package io.platformengineer.rdicpfhighvolume.address;

import com.redis.om.spring.annotations.GeoIndexed;
import org.springframework.data.geo.Point;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class AddressRedis {

    @NonNull
    private String street;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private String postalCode;

    @NonNull
    private String country;

    @GeoIndexed  // This annotation is crucial for geospatial operations in Redis
    private Point location; // This will store latitude and longitude
}
