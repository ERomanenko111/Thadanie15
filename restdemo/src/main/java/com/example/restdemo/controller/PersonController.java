package com.example.restdemo.controller;

import com.example.restdemo.dto.Person;
import com.example.restdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        repository.save(person);
        return person;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        Optional<Person> existingPerson = repository.findById(id);
        if (existingPerson.isPresent()) {
            person.setId(id);
            repository.save(person);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Iterable<Person> getPersons() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findPersonById(@PathVariable int id) {
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        repository.deleteById(id);
    }
}
