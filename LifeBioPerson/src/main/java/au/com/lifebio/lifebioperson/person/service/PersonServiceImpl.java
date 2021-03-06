package au.com.lifebio.lifebioperson.person.service;

import au.com.lifebio.lifebiocommon.common.exception.ConflictException;
import au.com.lifebio.lifebiocommon.common.exception.TypeNotSupportedException;
import au.com.lifebio.lifebioperson.person.Person;
import au.com.lifebio.lifebioperson.person.PersonImpl;
import au.com.lifebio.lifebioperson.person.dao.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/18.
 */

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    @PreAuthorize("hasAuthority('PERSON_CREATE')")
    public Optional<Person> addPerson(@NotNull(message = "Cannot add null person.") Person person) {
        person.setLastModified(LocalDateTime.now());
        return Optional.of(personRepository.save((PersonImpl)person));
    }

    @Override
    @PreAuthorize("hasAuthority('PERSON_UPDATE')")
    public Optional<Person> changePerson(@NotNull(message = "Cannot change null person.")Person person) {
        if(!person.getLastModified().equals(personRepository.findById(person.getOID()).orElseThrow(
                () -> new ResourceNotFoundException("Cannot change person, cannot find person!") )
                .getLastModified())){
            throw new ConflictException("Cannot change person, it has been modified by someone else!");
        }
        if(person instanceof PersonImpl) {
            person.setLastModified(LocalDateTime.now());
            return Optional.of(personRepository.save((PersonImpl)person));
        }
        throw new TypeNotSupportedException("Person is not a supported type!");
    }

    @Override
    @PreAuthorize("hasAuthority('PERSON_READ')")
    public Optional<Person> findPersonByIdentificationNumber(
            @NotEmpty(message = "Cannot find person with an empty identification number.") String identificationNumber) {
        Optional<Person> optional = Optional.of(personRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(
                () -> new ResourceNotFoundException("Unable to find person by identification number")
        ));
        return optional;
    }

    @Override
    @PreAuthorize("hasAuthority('PERSON_READ')")
    public Optional<Person> findPerson(@NotNull(message = "Cannot find person with a null OID.") Long oID) {
        Optional<Person> optional = Optional.of(personRepository.findById(oID).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find person by OID")
        ));
        return optional;
    }

    @Override
    @PreAuthorize("hasAuthority('PERSON_READ')")
    public Optional<Set<Person>> findAll() {
        return Optional.of(new HashSet<Person>(personRepository.findAll()));
    }

    @Override
    @PreAuthorize("hasAuthority('PERSON_READ')")
    public Optional<Set<Person>> findBySurnameLike(@NotEmpty(message = "Cannot find person with an empty surname.")
            String surnameLike) {
        return Optional.of(personRepository.findBySurnameLike(surnameLike));
    }

    @Override
    @PreAuthorize("hasAuthority('PERSON_DELETE')")
    public void deletePerson(@NotNull(message = "Cannot delete person, a valid person must be specified.")
                                         Person person) {
        if(person instanceof PersonImpl) {
            personRepository.delete((PersonImpl) person);
        }
        else {
            throw new TypeNotSupportedException("Person is not a supported type!");
        }

    }


}
