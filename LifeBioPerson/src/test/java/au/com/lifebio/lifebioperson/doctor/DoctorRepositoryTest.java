package au.com.lifebio.lifebioperson.doctor;

import au.com.lifebio.lifebioperson.doctor.dao.DoctorRepository;
import au.com.lifebio.lifebioperson.person.Person;
import au.com.lifebio.lifebioperson.person.doctor.Doctor;
import au.com.lifebio.lifebioperson.person.doctor.DoctorImpl;
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
public class DoctorRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void whenFindByPractiseNumber_thenReturnDoctor() {
        /* Given */
        String firstName = RandomStringUtils.random(10);
        String surname = RandomStringUtils.random(10);
        String middleName = RandomStringUtils.random(10);
        String nickName = RandomStringUtils.random(10);
        String identificationNumber = RandomStringUtils.random(10);
        Person.Gender gender = Person.Gender.MALE;
        Person.Title title = Person.Title.CAPT;
        String practiseNumber = RandomStringUtils.random(10);
        boolean isReferringDoctor = false;
        String serviceProviderName = RandomStringUtils.random(10);

        Doctor given = new DoctorImpl();
        given.setFirstName(firstName);
        given.setSurname(surname);
        given.setMiddleName(middleName);
        given.setNickName(nickName);
        given.setIdentificationNumber(identificationNumber);
        given.setGender(gender);
        given.setTitle(title);
        given.setPractiseNumber(practiseNumber);
        given.setReferringDoctor(isReferringDoctor);
        given.setServiceProviderName(serviceProviderName);
        given.setLastModified(LocalDateTime.now());

        entityManager.persist(given);
        entityManager.flush();

        /* When */
        Doctor found = doctorRepository.findByPractiseNumber(practiseNumber);

        /* Then */
        assertThat(found.getPractiseNumber()).isEqualTo(practiseNumber);
    }

}
