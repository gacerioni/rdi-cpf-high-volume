package io.platformengineer.rdicpfhighvolume.person;
import io.micrometer.core.annotation.Timed;
import io.platformengineer.rdicpfhighvolume.address.Address;
import io.platformengineer.rdicpfhighvolume.address.AddressRepository;
import io.platformengineer.rdicpfhighvolume.harnessfeatureflag.FeatureFlagManager;
import io.platformengineer.rdicpfhighvolume.vehicle.Vehicle;
import io.platformengineer.rdicpfhighvolume.vehicle.VehicleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private FeatureFlagManager featureFlagManager;


    @Transactional
    public Person createPerson(PersonCreationDTO dto) {
        Person person = new Person();
        BeanUtils.copyProperties(dto, person);

        Address address = new Address();
        BeanUtils.copyProperties(dto.getAddress(), address);
        address.setPerson(person);  // Link the address to the person
        person.setAddress(address);

        Set<Vehicle> vehicles = dto.getVehicles().stream().map(vehicleDTO -> {
            Vehicle vehicle = new Vehicle();
            BeanUtils.copyProperties(vehicleDTO, vehicle);
            vehicle.setPerson(person);
            return vehicle;
        }).collect(Collectors.toSet());

        person.setVehicles(vehicles);  // Now using the setter

        personRepository.save(person);  // This should cascade to vehicles and address
        return person;
    }

    /**
     * Introduces a randomized delay to simulate processing time.
     */
    private void simulateRandomDelay() {
        try {
            int minLatency = featureFlagManager.getMinLatency();
            int maxLatency = featureFlagManager.getMaxLatency();
            int delay = ThreadLocalRandom.current().nextInt(minLatency, maxLatency + 1);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // Handle the interruption appropriately, maybe log it or rethrow if needed
        }
    }

    public List<Person> findAll() {
        simulateRandomDelay();
        return personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        simulateRandomDelay();
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public List<Person> findByCPF(String cpf) {
        simulateRandomDelay();
        return personRepository.findByCPF(cpf);
    }

}
