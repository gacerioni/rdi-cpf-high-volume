package io.platformengineer.rdicpfhighvolume.address;

import com.redis.om.spring.annotations.GeoIndexed;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import org.springframework.data.geo.Point;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class AddressRedis {

    @NonNull
    @Searchable
    private String street;

    @NonNull
    @Indexed
    private String city;

    @NonNull
    @Indexed
    private String state;

    @NonNull
    private String postalCode;

    @NonNull
    @Indexed
    private String country;

    @GeoIndexed  // This annotation is crucial for geospatial operations in Redis
    private Point location; // This will store latitude and longitude
}
