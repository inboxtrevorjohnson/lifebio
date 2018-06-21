package au.com.lifebio.lifebioperson.person.dao;

import au.com.lifebio.lifebioperson.person.Person;
import au.com.lifebio.lifebioperson.person.PersonImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/18.
 */

@Repository
public interface PersonRepository extends JpaRepository<PersonImpl, Long> {

    Optional<Person> findByIdentificationNumber(String identificationNumber);

    Set<Person> findBySurnameLike(String surnameLike);
}
