package io.platformengineer.rdicpfhighvolume.vehicle;

import io.platformengineer.rdicpfhighvolume.person.PersonRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.SearchResult;

@Service
public class VehicleRedisService {
    @Autowired
    private VehicleRedisRepository vehicleRedisRepository;

    static private final String indexName = "io.platformengineer.rdicpfhighvolume.vehicle.VehicleRedisIdx";

    @Value("${redis.uri}")
    private String redisUri;


    public List<VehicleRedis> findNearbyVehicles(double longitude, double latitude, double radius, String unit) {
        Point location = new Point(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        // Assuming findByLocationNear is a custom method defined in the repository
        // If not, you'll need to add it to your repository based on your Redis OM configuration.

        List<VehicleRedis> nearbyVehicles = vehicleRedisRepository.findByLocationNear(location, distance);
        System.out.println("nearbyVehicles: " + nearbyVehicles.toArray().length);
        return nearbyVehicles;
    }

    public SearchResult findNearbyPersonViaVehicleGeoloc(double longitude, double latitude, double radius) {
        // we will use Jedis to perform ft.search directly against the @location field.
        // example: @location:[-43.2104874, -22.951916 2 km]
        try (JedisPooled jedis = new JedisPooled(redisUri)) {
            String query = String.format("@location:[%f %f %f km]", longitude, latitude, radius);
            SearchResult searchResult = jedis.ftSearch(indexName, query);
            //List<PersonRedis> nearbyPersons = searchResult.docs(PersonRedis.class);
            System.out.println("nearbyPersons: " + searchResult);
            return searchResult;
        } catch (Exception e) {
            System.err.println("Failed to search for nearby persons: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
