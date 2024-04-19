package io.platformengineer.rdicpfhighvolume.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByPersonCpf(Long personCPF);
}
