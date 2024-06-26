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

    private static final int MAX_PERSON_COUNT = 50000;  // Maximum number of persons allowed


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
            System.out.println("Maximum number of persons reached. Adjusting data or skipping insertions. Person Count: " + personCount);
            return;
        }

        for (int i = 0; i < 50; i++) {
            Person person = registerPerson();
            registerVehicleForPerson(person); // Ensures at least one vehicle per person
        }
    }

    @Transactional
    public void insertControlledData() {


        // Define a fixed CPF for predictability
        //long fixedCpf = 12345678901L; // Example CPF; ensure it meets your validation rules
        long fixedCpf = generateCpf(); // Generate a random CPF

        // Check if the CPF already exists to avoid duplicates
        if (personRepository.existsById(fixedCpf)) {
            System.out.println("Person with CPF already exists.");
            return;
        }

        // Create and save the Person
        Person person = new Person("Gabriel", "Cerioni", "gabriel.cerioni@platformengineer.io", 32, fixedCpf, "04569-900");
        person = personRepository.save(person); // Save and immediately flush to ensure it's committed
        System.out.println("Person registered successfully.");

        // Create and save the Address
        Address address = createAddress(person);
        System.out.println("Address registered successfully.");
        // Set the Address to the Person and save
        person.setAddress(address);
        System.out.println("Address linked to Person.");
        personRepository.save(person); // Save and immediately flush to ensure it's committed
        System.out.println("Person updated with Address.");


        List<Vehicle> vehicles = List.of(
                new Vehicle("XYZ-1234", "Civic", 2021, "Red", "Honda", -80.1918, 25.7617, 20250.99),
                new Vehicle("DEF-5678", "Corolla", 2020, "Blue", "Toyota", -118.2437, 34.0522, 18400.75),
                new Vehicle("GHI-9012", "Jetta", 2019, "Black", "Volkswagen", 12.4964, 41.9028, 15700.50),
                new Vehicle("JKL-3456", "Mustang", 2018, "Silver", "Ford", 139.6917, 35.6895, 26500.00),
                new Vehicle("MNO-7890", "Ibiza", 2017, "Yellow", "Seat", -3.7038, 40.4168, 14800.30),
                new Vehicle("PQR-1234", "Golf", 2016, "Green,BurroQuandoFoge", "Volkswagen", 13.4050, 52.5200, 13500.45), // Multiple colors
                new Vehicle("STU-5678", "Onix", 2015, "Orange", "Chevrolet", -38.5014, -12.9714, 11000.70),
                new Vehicle("VWX-9012", "Tucson", 2014, "Violet", "Hyundai", -60.0217, 3.1190, 17500.88),
                new Vehicle("YZA-3456", "Sandero", 2013, "Brown,Black", "Renault", -49.2648, 16.6869, 9000.55), // Multiple colors
                new Vehicle("BCD-7890", "Ka", 2012, "Pink", "Ford", -43.2104874, -22.951916, 8200.95)
        );


        Person finalPerson = person;
        vehicles.forEach(vehicle -> {
            vehicle.setPerson(finalPerson);
            vehicleRepository.save(vehicle);
        });
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

            // Generate a random price
            double price = faker.number().randomDouble(2, 15000, 50000);  // Generates price between 15,000 and 50,000

            // Randomly select a location from the list
            double[] selectedLocation = locations.get(random.nextInt(locations.size()));

            // Use selected coordinates for each vehicle
            Vehicle vehicle = new Vehicle(plate, model, year, color, brand, selectedLocation[0], selectedLocation[1], price);
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


    // Method to generate a random CPF as a long
    public static long generateCpf() {
        Random random = new Random();
        int[] cpf = new int[11];

        // Generate the first 9 digits randomly
        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }

        // Calculate the first check digit (10th digit)
        cpf[9] = calculateCheckDigit(cpf, 10);

        // Calculate the second check digit (11th digit)
        cpf[10] = calculateCheckDigit(cpf, 11);

        // Convert the integer array to a long
        long cpfNumber = 0;
        for (int i = 0; i < cpf.length; i++) {
            cpfNumber = cpfNumber * 10 + cpf[i];
        }

        return cpfNumber;
    }

    // Method to calculate CPF check digit
    private static int calculateCheckDigit(int[] cpf, int length) {
        int sum = 0;
        for (int i = 0; i < length - 1; i++) {
            sum += cpf[i] * (length - i);
        }
        int remainder = (sum * 10) % 11;
        return (remainder == 10 || remainder == 11) ? 0 : remainder;
    }

    // Method to format CPF into more readable form (xxx.xxx.xxx-xx)
    private static String formatCpf(String cpf) {
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }
}
