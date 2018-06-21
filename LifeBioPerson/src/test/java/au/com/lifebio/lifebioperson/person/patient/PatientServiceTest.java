package au.com.lifebio.lifebioperson.person.patient;

import au.com.lifebio.lifebioperson.person.patient.Patient;
import au.com.lifebio.lifebioperson.person.patient.PatientImpl;
import au.com.lifebio.lifebioperson.person.patient.dao.PatientRepository;
import au.com.lifebio.lifebioperson.person.patient.service.PatientService;
import au.com.lifebio.lifebioperson.person.patient.service.PatientServiceImpl;
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
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;
    @MockBean
    private PatientRepository patientRepository;


    /* Given */
    private Patient given = new PatientImpl();
    private String firstName = RandomStringUtils.random(10);
    private String surname = RandomStringUtils.random(10);
    private String middleName = RandomStringUtils.random(10);
    private String nickName = RandomStringUtils.random(10);
    private String identificationNumber = RandomStringUtils.random(10);
    private Patient.Gender gender = Patient.Gender.MALE;
    private Patient.Title title = Patient.Title.CAPT;
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

        Optional<Patient> optional = Optional.of(given);
        Optional<PatientImpl> optionalImpl = Optional.of((PatientImpl) given);

        /* Add and Change */
        Mockito.when(patientRepository.save((PatientImpl)given)).thenReturn((PatientImpl) given);

        /* Find By ID */
        Mockito.when(patientRepository.findById(given.getOID())).thenReturn(optionalImpl);

        /* Find All */
        PatientImpl [] list = {new PatientImpl(), new PatientImpl(), new PatientImpl(), new PatientImpl(), new PatientImpl()};
        Mockito.when(patientRepository.findAll()).thenReturn(Arrays.asList(list));

    }

    @Test
    public void whenAdd_thenPatientShouldBeFound() {
        Optional<Patient> added = patientService.addPatient(patientRepository.save((PatientImpl) given));

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getIdentificationNumber()).isEqualTo(identificationNumber);
    }

    @Test
    public void whenChange_thenChangedPatientShouldBeFound() {
        Optional<Patient> added = patientService.addPatient(patientRepository.save((PatientImpl) given));

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getIdentificationNumber()).isEqualTo(identificationNumber);

        added.get().setFirstName(RandomStringUtils.random(10));
        added.get().setSurname(RandomStringUtils.random(10));
        added.get().setMiddleName(RandomStringUtils.random(10));
        added.get().setNickName(RandomStringUtils.random(10));
        added.get().setIdentificationNumber(RandomStringUtils.random(10));
        added.get().setGender(Patient.Gender.FEMALE);
        added.get().setTitle(Patient.Title.MR);

        Optional<Patient> changedOptional = patientService.changePatient(added.get());

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
    public void whenValidOID_thenPatientShouldBeFound() {
        Long oID = patientRepository.save((PatientImpl) given).getOID();
        Optional<Patient> found = patientService.findPatient(oID);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

    @Test
    public void whenFindAll_thenAllShouldBeFound() {
        Optional<Set<Patient>> optional = patientService.findAll();

        /* Check */
        assertThat(optional.isPresent());
        assertThat(optional.get().size()).isEqualTo(5);
    }

    @Test
    public void whenPatientDeleted_thenShouldNotBeFound() {
        Optional<Patient> found = Optional.of(patientRepository.save((PatientImpl) given));
        assertThat(found.isPresent());
        patientService.deletePatient(found.get());
        Mockito.verify(patientRepository).delete((PatientImpl) found.get());
    }

    @TestConfiguration
    static class PatientServiceImplTestContextConfiguration {

        @Bean
        public PatientService patientService() {
            return new PatientServiceImpl();
        }
    }

}
