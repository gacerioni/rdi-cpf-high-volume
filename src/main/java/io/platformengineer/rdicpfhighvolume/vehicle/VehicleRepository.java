package io.platformengineer.rdicpfhighvolume.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByPersonCpf(Long personCPF);
    Optional<Vehicle> findByIdAndPersonCpf(Long vehicleId, Long personId);
}
