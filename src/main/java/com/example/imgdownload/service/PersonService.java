package com.example.imgdownload.service;

import com.example.imgdownload.model.Person;
import com.example.imgdownload.repositories.PersonRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Data
public class PersonService {

    private final PersonRepository personRepository;

    public Person findOne(int id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void updateLogin(int id, String login) {
        Optional<Person> person = personRepository.findById(id);
        Person obj = person.get();
        obj.setLogin(login);
    }

}
