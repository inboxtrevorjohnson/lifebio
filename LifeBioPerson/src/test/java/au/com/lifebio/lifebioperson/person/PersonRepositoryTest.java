package au.com.lifebio.lifebioperson.person;

import au.com.lifebio.lifebioperson.person.dao.PersonRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Trevor on 2018/06/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void whenFindByIdentificationNumber_thenReturnEmployee() {
        /* Given */
        String firstName = RandomStringUtils.random(10);
        String surname = RandomStringUtils.random(10);
        String middleName = RandomStringUtils.random(10);
        String nickName = RandomStringUtils.random(10);
        String identificationNumber = RandomStringUtils.random(10);
        Person.Gender gender = Person.Gender.MALE;
        Person.Title title = Person.Title.CAPT;

        Person given = new PersonImpl();
        given.setFirstName(firstName);
        given.setSurname(surname);
        given.setMiddleName(middleName);
        given.setNickName(nickName);
        given.setIdentificationNumber(identificationNumber);
        given.setGender(gender);
        given.setTitle(title);
        given.setLastModified(LocalDateTime.now());

        entityManager.persist(given);
        entityManager.flush();

        /* When */
        Person found = personRepository.findByIdentificationNumber(identificationNumber);

        /* Then */
        assertThat(found.getIdentificationNumber()).isEqualTo(identificationNumber);
    }

}
