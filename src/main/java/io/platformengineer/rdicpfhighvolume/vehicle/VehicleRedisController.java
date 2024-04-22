package io.platformengineer.rdicpfhighvolume.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.search.SearchResult;

import java.util.List;

@RestController
@RequestMapping("/vehicles-redis")
public class VehicleRedisController {
    @Autowired
    private VehicleRedisService vehicleRedisService;

    @GetMapping("/nearby")
    // Use findNearbyPersonViaVehicleGeoloc method from VehicleRedisService.java
    public ResponseEntity<SearchResult> findNearbyPersonViaVehicleGeoloc(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
        SearchResult searchResult = vehicleRedisService.findNearbyPersonViaVehicleGeoloc(longitude, latitude, radius);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
