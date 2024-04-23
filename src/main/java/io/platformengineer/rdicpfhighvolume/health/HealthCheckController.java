package io.platformengineer.rdicpfhighvolume.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.platformengineer.rdicpfhighvolume.person.PersonService;

@RestController
@RequestMapping("/gabs")
public class HealthCheckController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<String> checkDatabaseHealth() {
        if (personService.isDatabaseConnectionAlive()) {
            return ResponseEntity.ok("Database is up!");
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Database is down!");
        }
    }
}