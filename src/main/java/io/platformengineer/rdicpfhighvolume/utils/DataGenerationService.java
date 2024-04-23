package io.platformengineer.rdicpfhighvolume.utils;

import com.github.javafaker.Faker;
import io.platformengineer.rdicpfhighvolume.person.Person;
import io.platformengineer.rdicpfhighvolume.person.PersonRepository;
import io.platformengineer.rdicpfhighvolume.address.Address;
import io.platformengineer.rdicpfhighvolume.address.AddressRepository;
import io.platformengineer.rdicpfhighvolume.vehicle.Vehicle;
import io.platformengineer.rdicpfhighvolume.vehicle.VehicleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;


@Service
@Transactional
public class DataGenerationService {

    private final Faker faker = new Faker(new Locale("pt-BR"));
    private final Random random = new Random();

    @Value("${faker-data-generation.enabled:true}")
    private boolean dataGenerationEnabled;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EntityManager entityManager;

    private static final int MAX_PERSON_COUNT = 3000;  // Maximum number of persons allowed


    @PostConstruct
    public void init() {
        //insertControlledData();
    }

    @Scheduled(fixedDelay = 5000)
    public void registerPersonAndVehicle() {
        if (!dataGenerationEnabled) {
            return;
        }

        long personCount = personRepository.count();
        if (personCount >= MAX_PERSON_COUNT) {
            System.out.println("Maximum number of persons reached. Adjusting data or skipping insertions.");
            return;
        }

        for (int i = 0; i < 3; i++) {
            Person person = registerPerson();
            registerVehicleForPerson(person); // Ensures at least one vehicle per person
        }
    }

    private void insertControlledData() {
        Random random = new Random();
        long cpf;
        boolean cpfExists;

        do {
            cpf = random.nextLong(10000000000L, 99999999999L); // Ensure 11 digits
            cpfExists = personRepository.existsById(cpf);
        } while (cpfExists);

        // Define controlled data based on your specified requirements
        Person person = new Person("Gabriel", "Cerioni", "gabriel.cerioni@platformengineer.io", 32, cpf, "04128000");
        person = personRepository.save(person); // Save and immediately flush to ensure it's committed

        Address address = new Address("Rua dos Alfeneiros 4", "Sao Paulo", "Sao Paulo", "04128000", "Brazil");
        address.setPerson(person);
        addressRepository.save(address); // Save and immediately flush

        person.setAddress(address);
        /*
        // Add vehicles
        List<Vehicle> vehicles = List.of(
                new Vehicle("IBI-9087", "Civic", 2020, "Red", "Honda", -46.660683, -23.601852),
                new Vehicle("BEL-3142", "3 Series", 2021, "Black", "BMW", -43.934559, -19.917299),
                new Vehicle("RIO-6789", "Golf", 2019, "White", "Volkswagen", -43.172897, -22.906847),
                new Vehicle("POA-4321", "X5", 2020, "Blue", "BMW", -51.217659, -30.034647),
                new Vehicle("MAN-9023", "Passat", 2018, "Silver", "Volkswagen", -60.021732, -3.119028),
                new Vehicle("NYC-1234", "320i", 2022, "Grey", "BMW", -74.0060152, 40.7127281),
                new Vehicle("PER-8901", "Tiguan", 2021, "Black", "Volkswagen", -46.631082477201446, -23.602452414425716),
                new Vehicle("JAP-5566", "Accord", 2020, "White", "Honda", 139.762221, 35.6821936),
                new Vehicle("ITA-1425", "3 Series", 2018, "Blue", "BMW", 12.4829321, 41.8933203)
        );
        */// Ensure all vehicles are flushed at once after the loop
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
