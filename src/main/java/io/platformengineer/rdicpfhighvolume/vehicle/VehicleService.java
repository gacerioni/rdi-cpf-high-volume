package io.platformengineer.rdicpfhighvolume.vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle addVehicle(Long personId, Vehicle vehicle);
    List<Vehicle> findAllVehiclesByCPFId(Long personId);
    Vehicle findVehicleByIdAndCPFId(Long vehicleId, Long personId);
    Vehicle updateVehicle(Long personId, Long vehicleId, Vehicle vehicleDetails);
    void deleteVehicle(Long personId, Long vehicleId);
}
