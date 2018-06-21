package au.com.lifebio.lifebioperson.person.service;

import au.com.lifebio.lifebioperson.person.Person;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/21.
 */

public interface PersonService {

    Optional<Person> addPerson(Person person);

    Optional<Person> changePerson(Person person);

    Optional<Person> findPerson(Long oID);

    Optional<Person> findPersonByIdentificationNumber(String identificationNumber);

    Optional<Set<Person>> findAll();

    Optional<Set<Person>> findBySurnameLike(String surnameLike);

    void deletePerson(Person person);

}
