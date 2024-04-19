package io.platformengineer.rdicpfhighvolume.vehicle;

import io.platformengineer.rdicpfhighvolume.person.Person;
import io.platformengineer.rdicpfhighvolume.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final PersonRepository personRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(PersonRepository personRepository, VehicleRepository vehicleRepository) {
        this.personRepository = personRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle addVehicle(Long personId, Vehicle vehicle) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found with id " + personId));
        vehicle.setPerson(person);
        return null;
    }

    @Override
    public List<Vehicle> findAllVehiclesByCPFId(Long personId) {
        return vehicleRepository.findAllByPersonCpf(personId);
    }

    @Override
    public Vehicle findVehicleByIdAndCPFId(Long vehicleId, Long personId) {
        return null;
    }

    @Override
    public Vehicle updateVehicle(Long personId, Long vehicleId, Vehicle vehicleDetails) {
        return null;
    }

    @Override
    public void deleteVehicle(Long personId, Long vehicleId) {

    }
}
