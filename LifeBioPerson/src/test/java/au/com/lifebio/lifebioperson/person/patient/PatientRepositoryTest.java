package au.com.lifebio.lifebioperson.person.patient;

import au.com.lifebio.lifebioperson.person.Person;
import au.com.lifebio.lifebioperson.person.patient.dao.PatientRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Trevor on 2018/06/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void whenFindByOID_thenReturnPatient() {
        /* Given */
        String firstName = RandomStringUtils.random(10);
        String surname = RandomStringUtils.random(10);
        String middleName = RandomStringUtils.random(10);
        String nickName = RandomStringUtils.random(10);
        String identificationNumber = RandomStringUtils.random(10);
        Person.Gender gender = Person.Gender.MALE;
        Person.Title title = Person.Title.CAPT;

        Patient given = new PatientImpl();
        given.setFirstName(firstName);
        given.setSurname(surname);
        given.setMiddleName(middleName);
        given.setNickName(nickName);
        given.setIdentificationNumber(identificationNumber);
        given.setGender(gender);
        given.setTitle(title);
        given.setLastModified(LocalDateTime.now());

        Long oID = entityManager.persist(given).getOID();
        entityManager.flush();

        /* When */
        Optional<PatientImpl> found = patientRepository.findById(oID);

        /* Then */
        assertThat(found.isPresent());
        assertThat((found.get().getOID())).isEqualTo(oID);
    }
}
