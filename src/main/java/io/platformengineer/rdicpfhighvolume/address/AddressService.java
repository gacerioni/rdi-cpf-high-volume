package io.platformengineer.rdicpfhighvolume.address;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import io.platformengineer.rdicpfhighvolume.person.PersonService;
import io.platformengineer.rdicpfhighvolume.person.Person;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonService personService;

    @Transactional
    public Address createAddress(Long cpf, Address address) {
        // Retrieve the associated person, or throw exception if not found
        Person person = personService.findById(cpf).orElseThrow(() -> new RuntimeException("Person not found"));

        // Set the ID of the address to the person's CPF and link the address to the person
        address.setId(cpf);
        address.setPerson(person);

        // Save the new address
        addressRepository.save(address);

        // Set the new address to the person and save the person entity
        person.setAddress(address);
        personService.save(person);

        return address;
    }

}
