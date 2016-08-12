package com.cver.team.services.impl;

import com.cver.team.model.entity.Person;
import com.cver.team.persistence.PersonRepository;
import com.cver.team.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PersonServiceImpl implements PersonService {


    @Autowired
    PersonRepository personRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Person saveNewPerson(Person person) {

        if(person.getPassword() != null)
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));

       return  personRepository.savePerson(person);
    }

    @Override
    public Person getPersonByLoginEmail(String email) {
        return personRepository.getPersonByLoginEmail(email);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return personRepository.isEmailTaken(email);
    }

    @Override
    public Person getPersonByLoginEmailWithoutPassword(String email) {
        Person person = getPersonByLoginEmail(email);
        if(person != null)
        person.setPassword("");
        return person;
    }

    @Override
    public Person deletePerson(Person person) {
        return null;
    }

    @Override
    public Person getPersonById(String id) {
        Person person = personRepository.getPersonById(id);
        if (person != null)
            person.setPassword("");
        return person;
    }
}
