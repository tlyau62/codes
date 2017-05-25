package com.example.WebDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by tl on 5/23/17.
 */

@RestController
@RequestMapping("/api")
public class PersonController {

    private final Logger log = LoggerFactory.getLogger(PersonController.class);

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        log.info("Log: createPerson {}", person);

        return personRepository.get(person.getId())
                .map((p) -> new ResponseEntity<>(person, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    personRepository.save(person);
                    return new ResponseEntity<>(person, HttpStatus.CREATED);
                });

    }

    @GetMapping("/people")
    public ResponseEntity<List<Person>> getAllPeople() {
        log.info("log: getAllPeople");

        return new ResponseEntity<>(personRepository.getAll(), HttpStatus.OK);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        log.info("Log: getPerson {}", id);

        return personRepository.get(id)
                .map((person -> new ResponseEntity<>(person, HttpStatus.OK)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable Long id) {
        log.info("Log: deletePerson {}", id);

        return personRepository.delete(id)
                .map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
