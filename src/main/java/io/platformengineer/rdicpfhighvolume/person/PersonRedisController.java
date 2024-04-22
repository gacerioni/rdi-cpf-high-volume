package io.platformengineer.rdicpfhighvolume.person;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/person-redis")
public class PersonRedisController {

    @Autowired
    private PersonRedisService personRedisService;

    // Method to retrieve all persons from Redis
    @GetMapping
    public Iterable<PersonRedis> findAll() {
        return personRedisService.findAll();
    }

    // Method to retrieve a person by CPF from Redis
    @GetMapping("/{cpf}")
    public Optional<PersonRedis> findByCPF(@PathVariable Long cpf) {
        return personRedisService.findByCPF(String.valueOf(cpf));
    }
}
