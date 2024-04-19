package io.platformengineer.rdicpfhighvolume.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/person-redis")
public class PersonRedisController {

    @Autowired
    private PersonRedisRepository personRedisRepository;

    @GetMapping
    public Iterable<PersonRedis> findAll() {
        return personRedisRepository.findAll();
    }

    @GetMapping("/{cpf}")
    public Optional<PersonRedis> findById(@PathVariable Long cpf) {
        return personRedisRepository.findById(String.valueOf(cpf));
    }
}
