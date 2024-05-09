package io.platformengineer.rdicpfhighvolume.utils;

import io.platformengineer.rdicpfhighvolume.person.Person;
import io.platformengineer.rdicpfhighvolume.person.PersonRepository;
import io.platformengineer.rdicpfhighvolume.address.Address;
import io.platformengineer.rdicpfhighvolume.address.AddressRepository;
import io.platformengineer.rdicpfhighvolume.vehicle.Vehicle;
import io.platformengineer.rdicpfhighvolume.vehicle.VehicleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import jakarta.annotation.PostConstruct;

@Service
@Transactional
public class CSVDataLoaderService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private EntityManager entityManager;



    public void init() {
        loadCSVData();
    }

    public void loadCSVData() {
        loadPersons();
        loadVehicles();
    }


    private void loadPersons() {
        String personCsv = "predictableDataSources/persons.csv";
        try (Reader reader = new InputStreamReader(new ClassPathResource(personCsv).getInputStream())) {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord record : parser) {
                System.out.println("Loading person: " + record.get("first_name") + " " + record.get("last_name"));
                Long cpf = Long.parseLong(record.get("cpf"));
                Person person = new Person(
                        record.get("first_name"),
                        record.get("last_name"),
                        record.get("email"),
                        Integer.parseInt(record.get("age")),
                        cpf,
                        record.get("zip_code")
                );
                personRepository.save(person);

                // now we load the addresses to MySQL
                loadAddresses();

                entityManager.flush(); // Forces commit of changes up to this point

                // Now we also search for the cpf id in the address file CSV, 1-1, and set the person address to the person
                // This is a 1-1 relationship, so we can do this
                Long personCpf = Long.parseLong(record.get("cpf"));
                // use this address to set the person address
                Optional<Address> address = addressRepository.findById(personCpf);


                if (address.isPresent()) {
                    person.setAddress(address.get());
                    personRepository.save(person);
                    System.out.println("Address saved successfully for person with CPF: " + personCpf);
                } else {
                    System.err.println("No address found for person with CPF: " + personCpf);
                }

                entityManager.flush(); // Forces commit of changes up to this point
            }
        } catch (Exception e) {
            System.err.println("Error loading persons: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadAddresses() {
        String addressCsv = "predictableDataSources/addresses.csv";
        try (Reader reader = new InputStreamReader(new ClassPathResource(addressCsv).getInputStream())) {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord record : parser) {
                Long personCpf = Long.parseLong(record.get("person_cpf"));
                Person person = personRepository.findById(personCpf).orElse(null);
                if (person != null) {
                    Address address = new Address(
                            record.get("street"),
                            record.get("city"),
                            record.get("state"),
                            record.get("postal_code"),
                            record.get("country")
                    );
                    System.out.println("Loading address: " + address.getStreet() + ", " + address.getCity() + ", " + address.getState() + ", " + address.getPostalCode() + ", " + address.getCountry());
                    address.setPerson(person);
                    addressRepository.save(address);
                    System.out.println("Address saved successfully.");
                } else {
                    System.err.println("No person found with CPF: " + personCpf);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading addresses: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadVehicles() {
        String vehicleCsv = "predictableDataSources/vehicles.csv";
        try (Reader reader = new InputStreamReader(new ClassPathResource(vehicleCsv).getInputStream())) {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord record : parser) {
                Vehicle vehicle = new Vehicle(
                        record.get("plate"),
                        record.get("model"),
                        Integer.parseInt(record.get("year")),
                        record.get("color"),
                        record.get("brand"),
                        Double.parseDouble(record.get("longitude")),
                        Double.parseDouble(record.get("latitude")),
                        Double.parseDouble(record.get("price"))

                );
                vehicle.setPerson(personRepository.findById(Long.parseLong(record.get("person_cpf"))).orElse(null));
                vehicleRepository.save(vehicle);
            }
        } catch (Exception e) {
            System.err.println("Error loading vehicles: " + e.getMessage());
            e.printStackTrace();
        }
    }
}