package io.platformengineer.rdicpfhighvolume.vehicle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Endpoint to create a vehicle for a person
    @PostMapping("/{cpf}")
    public ResponseEntity<?> createVehicle(@PathVariable Long cpf, @RequestBody Vehicle vehicle) {
        try {
            Vehicle newVehicle = vehicleService.addVehicle(cpf, vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).body(newVehicle);
        } catch (RuntimeException ex) {
            // More detailed error handling
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
        }
    }
}
