package io.platformengineer.rdicpfhighvolume.person;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> findAll() {
        return personService.findAll();
    }

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

    @GetMapping("/find/cpf/{cpf}")
    public List<Person> findByCPF(@PathVariable String cpf) {
        return personService.findByCPF(cpf);
    }


}
