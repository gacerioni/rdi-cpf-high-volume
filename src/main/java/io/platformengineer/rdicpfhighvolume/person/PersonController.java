package io.platformengineer.rdicpfhighvolume.person;


import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    // Create general person
    @PostMapping("/")
    public ResponseEntity<Person> createPerson(@RequestBody PersonCreationDTO personCreationDTO) {
        Person savedPerson = personService.createPerson(personCreationDTO);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    // Create person with details
    @PostMapping("/withDetails")
    public ResponseEntity<Person> createPersonWithDetails(@RequestBody Person person) {
        Person savedPerson = personService.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @Timed(value = "person.find_all_mysql", description = "Time taken to retrieve all persons from MySQL")
    @GetMapping
    public List<Person> findAll() {
        return personService.findAll();
    }

    @Timed(value = "person.find_by_id_mysql", description = "Time taken to retrieve a person by id from MySQL")
    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable Long id) {
        return personService.findById(id);
    }

    // create a book
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Person create(@RequestBody Person person) {
        return personService.save(person);
    }

    // update a book
    @PutMapping
    public Person update(@RequestBody Person person) {
        return personService.save(person);
    }

    // delete a book
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        personService.deleteById(id);
    }

    @Timed(value = "person.find_by_cpf_mysql", description = "Time taken to retrieve a person by CPF from MySQL")
    @GetMapping("/find/cpf/{cpf}")
    public List<Person> findByCPF(@PathVariable String cpf) {
        return personService.findByCPF(cpf);
    }

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        if (personService.isDatabaseConnectionAlive()) {
            return ResponseEntity.ok("Database is up!");
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Database is down!");
        }
    }



}
