package io.platformengineer.rdicpfhighvolume.utils;

import com.github.javafaker.Faker;
import io.platformengineer.rdicpfhighvolume.person.Person;
import io.platformengineer.rdicpfhighvolume.person.PersonRepository;
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

@Service
@Transactional
public class DataGenerationService {

    private final Faker faker = new Faker(new Locale("pt-BR"));

    @Autowired
    private PersonRepository personRepository; // Autowired to inject the repository
    @Autowired
    private VehicleRepository vehicleRepository;

    public DataGenerationService() {
    }

    @PostConstruct
    public void init() {
        // Initial data load if necessary
        // This method can be used to load initial data when the application starts
    }

    @Scheduled(fixedDelay = 3000)
    public void registerPersonAndVehicle() {
        for (int i = 0; i < 3; i++) {
            Person person = registerPerson();
            registerVehicleForPerson(person);
        }
    }

    private Person createPerson() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@platformengineer.io", firstName, lastName);
        Integer age = faker.number().numberBetween(17, 55);
        Long cpf = faker.number().randomNumber(11, true);
        String zipCode = faker.bothify("#####-###");

        return new Person(firstName, lastName, email, age, cpf, zipCode);
    }

    private Person registerPerson() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@platformengineer.io", firstName, lastName);
        Integer age = faker.number().numberBetween(17, 55);
        Long cpf = faker.number().randomNumber(11, true);
        String zipCode = faker.bothify("#####-###");

        Person person = new Person(firstName, lastName, email, age, cpf, zipCode);

        return personRepository.save(person);
    }

    private void registerVehicleForPerson(Person person) {
        List<Vehicle> vehicles = new ArrayList<>();

        // Create some vehicles
        Vehicle vehicle1 = new Vehicle("ABC-123", "Corolla", 2021, "black", "Toyota", -12.12345, -47.123);
        Vehicle vehicle2 = new Vehicle("XYZ-456", "Civic", 2020, "white", "Honda", -12.12345, -47.123);
        Vehicle vehicle3 = new Vehicle("DEF-789", "Golf", 2019, "red", "Volkswagen", -12.12345, -47.123);

        vehicle1.setPerson(person);
        vehicle2.setPerson(person);
        vehicle3.setPerson(person);

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);

        vehicleRepository.saveAll(vehicles);


    }
}
