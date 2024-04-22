package io.platformengineer.rdicpfhighvolume.utils;

import com.github.javafaker.Faker;
import io.platformengineer.rdicpfhighvolume.person.Person;
import io.platformengineer.rdicpfhighvolume.person.PersonRepository;
import io.platformengineer.rdicpfhighvolume.address.Address;
import io.platformengineer.rdicpfhighvolume.address.AddressRepository;
import io.platformengineer.rdicpfhighvolume.vehicle.Vehicle;
import io.platformengineer.rdicpfhighvolume.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
@Transactional
public class DataGenerationService {

    private final Faker faker = new Faker(new Locale("pt-BR"));
    private final Random random = new Random();

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private AddressRepository addressRepository;

    @PostConstruct
    public void init() {
        // Initial data load if necessary
    }

    @Scheduled(fixedDelay = 3000)
    public void registerPersonAndVehicle() {
        for (int i = 0; i < 3; i++) {
            Person person = registerPerson();
            registerVehicleForPerson(person); // Ensures at least one vehicle per person
        }
    }

    private Person registerPerson() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@platformengineer.io", firstName, lastName);
        Integer age = faker.number().numberBetween(17, 55);
        Long cpf = faker.number().randomNumber(11, true);
        String zipCode = faker.bothify("#####-###");

        // Create person and save to get an ID
        Person person = new Person(firstName, lastName, email, age, cpf, zipCode);
        person = personRepository.save(person);  // Save person to assign an ID

        Address address = createAddress(person);  // Pass person to create Address
        person.setAddress(address);

        // Save address separately if needed, or rely on cascade from saving person again
        return personRepository.save(person);  // Save the person again with the address set
    }

    private Address createAddress(Person person) {
        String street = faker.address().streetName();
        String city = faker.address().city();
        String state = faker.address().state();
        String postalCode = faker.address().zipCode();
        String country = faker.address().country();

        Address address = new Address(street, city, state, postalCode, country);
        address.setPerson(person);  // Link person to address
        return addressRepository.save(address);  // Save and return address
    }

    private void registerVehicleForPerson(Person person) {
        List<Vehicle> vehicles = new ArrayList<>();
        int numVehicles = random.nextInt(5) + 1; // Generates 1 to 5 vehicles

        // List of hardcoded locations
        List<double[]> locations = List.of(
                new double[]{-43.2104874, -22.951916},   // Christ the Redeemer, Brazil
                new double[]{12.4922309, 41.8902102},   // Colosseum, Italy
                new double[]{-74.0445023, 40.6892494},  // Statue of Liberty, USA
                new double[]{35.2340985, 31.7766874},   // Western Wall, Israel
                new double[]{13.377704, 52.5162746},    // Brandenburg Gate, Germany
                new double[]{139.744732, 35.6585805},   // Tokyo Tower, Japan
                new double[]{2.2944813, 48.8583701}     // Eiffel Tower, France
        );

        for (int i = 0; i < numVehicles; i++) {
            String plate = faker.bothify("???-####");
            String model = faker.options().option("Corolla", "Civic", "Golf");
            int year = faker.number().numberBetween(2010, 2021);
            String color = faker.color().name();
            String brand = faker.company().name();

            // Randomly select a location from the list
            double[] selectedLocation = locations.get(random.nextInt(locations.size()));

            // Use selected coordinates for each vehicle
            Vehicle vehicle = new Vehicle(plate, model, year, color, brand, selectedLocation[0], selectedLocation[1]);
            vehicle.setPerson(person);
            vehicles.add(vehicle);
        }

        try {
            vehicleRepository.saveAll(vehicles);
            //System.out.println("Vehicles registered successfully with randomized locations.");
        } catch (Exception e) {
            System.err.println("Error registering vehicles: " + e.getMessage());
        }
    }
}
