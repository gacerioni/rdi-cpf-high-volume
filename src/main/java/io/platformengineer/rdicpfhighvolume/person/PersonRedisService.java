package io.platformengineer.rdicpfhighvolume.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.micrometer.core.annotation.Timed;
import java.util.Optional;

@Service
public class PersonRedisService {

    @Autowired
    private PersonRedisRepository personRedisRepository;

    @Timed(value = "person.find_all_redis", description = "Time taken to retrieve all persons from Redis")
    public Iterable<PersonRedis> findAll() {
        return personRedisRepository.findAll();
    }

    @Timed(value = "person.find_by_cpf_redis", description = "Time taken to retrieve a person by CPF from Redis")
    public Optional<PersonRedis> findByCPF(String cpf) {
        return personRedisRepository.findById(cpf);
    }
}