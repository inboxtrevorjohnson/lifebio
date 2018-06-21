package au.com.lifebio.lifebioperson.person;

import au.com.lifebio.lifebioperson.person.dao.PersonRepository;
import au.com.lifebio.lifebioperson.person.service.PersonService;
import au.com.lifebio.lifebioperson.person.service.PersonServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Trevor on 2018/06/18.
 */
@RunWith(SpringRunner.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;
    @MockBean
    private PersonRepository personRepository;


    /* Given */
    private Person given = new PersonImpl();
    private String firstName = RandomStringUtils.random(10);
    private String surname = RandomStringUtils.random(10);
    private String middleName = RandomStringUtils.random(10);
    private String nickName = RandomStringUtils.random(10);
    private String identificationNumber = RandomStringUtils.random(10);
    private Person.Gender gender = Person.Gender.MALE;
    private Person.Title title = Person.Title.CAPT;
    private LocalDateTime lastModified = LocalDateTime.now();
    private String like = RandomStringUtils.random(10);

    @Before
    public void setUp() {
        given.setFirstName(firstName);
        given.setSurname(surname);
        given.setMiddleName(middleName);
        given.setNickName(nickName);
        given.setIdentificationNumber(identificationNumber);
        given.setGender(gender);
        given.setTitle(title);
        given.setLastModified(lastModified);

        Optional<Person> optional = Optional.of(given);
        Optional<PersonImpl> optionalImpl = Optional.of((PersonImpl) given);

        /* Add and Change */
        Mockito.when(personRepository.save((PersonImpl)given)).thenReturn((PersonImpl) given);

        /* Find By ID */
        Mockito.when(personRepository.findById(given.getOID())).thenReturn(optionalImpl);

        /* Find All */
        PersonImpl [] list = {new PersonImpl(), new PersonImpl(), new PersonImpl(), new PersonImpl(), new PersonImpl()};
        Mockito.when(personRepository.findAll()).thenReturn(Arrays.asList(list));

        /* Find Like */
        Set<Person> set = Arrays.stream(list).limit(3).collect(Collectors.toSet());
        set.forEach(person -> person.setSurname(like));
        Mockito.when(personRepository.findBySurnameLike(like)).thenReturn(set);

        /* Find By Identification Number */
        Mockito.when(personRepository.findByIdentificationNumber(given.getIdentificationNumber())).thenReturn(optional);

    }

    @Test
    public void whenAdd_thenPersonShouldBeFound() {
        Optional<Person> added = personService.addPerson(personRepository.save((PersonImpl) given));

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getIdentificationNumber()).isEqualTo(identificationNumber);
    }

    @Test
    public void whenChange_thenChangedPersonShouldBeFound() {
        Optional<Person> added = personService.addPerson(personRepository.save((PersonImpl) given));

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getIdentificationNumber()).isEqualTo(identificationNumber);

        added.get().setFirstName(RandomStringUtils.random(10));
        added.get().setSurname(RandomStringUtils.random(10));
        added.get().setMiddleName(RandomStringUtils.random(10));
        added.get().setNickName(RandomStringUtils.random(10));
        added.get().setIdentificationNumber(RandomStringUtils.random(10));
        added.get().setGender(Person.Gender.FEMALE);
        added.get().setTitle(Person.Title.MR);

        Optional<Person> changedOptional = personService.changePerson(added.get());

        /* Check Was Changed */
        assertThat(changedOptional.isPresent());
        assertThat(changedOptional.get().getFirstName()).isNotEqualTo(firstName);
        assertThat(changedOptional.get().getSurname()).isNotEqualTo(surname);
        assertThat(changedOptional.get().getMiddleName()).isNotEqualTo(middleName);
        assertThat(changedOptional.get().getNickName()).isNotEqualTo(nickName);
        assertThat(changedOptional.get().getIdentificationNumber()).isNotEqualTo(identificationNumber);
        assertThat(changedOptional.get().getGender()).isNotEqualTo(gender);
        assertThat(changedOptional.get().getTitle()).isNotEqualTo(title);
        assertThat(changedOptional.get().getLastModified()).isNotEqualTo(lastModified);

    }

    @Test
    public void whenValidOID_thenPersonShouldBeFound() {
        Long oID = personRepository.save((PersonImpl) given).getOID();
        Optional<Person> found = personService.findPerson(oID);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

    @Test
    public void whenValidIdentificationNumber_thenPersonShouldBeFound() {
        Optional<Person> found = personService.findPersonByIdentificationNumber(identificationNumber);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getIdentificationNumber()).isEqualTo(identificationNumber);
    }

    @Test
    public void whenFindAll_thenAllShouldBeFound() {
        Optional<Set<Person>> optional = personService.findAll();

        /* Check */
        assertThat(optional.isPresent());
        assertThat(optional.get().size()).isEqualTo(5);
    }

    @Test
    public void whenFindBySurnameLike_thenOnlyLikeSurnameShouldBeFound() {
        Optional<Set<Person>> optional = personService.findBySurnameLike(like);

        /* Check */
        assertThat(optional.isPresent());
        assertThat(optional.get().size()).isEqualTo(3);
    }

    @Test
    public void whenPersonDeleted_thenShouldNotBeFound() {
        Optional<Person> found = Optional.of(personRepository.save((PersonImpl) given));
        assertThat(found.isPresent());
        personService.deletePerson(found.get());
        Mockito.verify(personRepository).delete((PersonImpl) found.get());
    }

    @TestConfiguration
    static class PersonServiceImplTestContextConfiguration {

        @Bean
        public PersonService personService() {
            return new PersonServiceImpl();
        }
    }

}
