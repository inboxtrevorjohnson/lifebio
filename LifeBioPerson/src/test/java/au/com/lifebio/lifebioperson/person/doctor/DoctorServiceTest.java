package au.com.lifebio.lifebioperson.person.doctor;

import au.com.lifebio.lifebioperson.person.doctor.dao.DoctorRepository;
import au.com.lifebio.lifebioperson.person.doctor.service.DoctorService;
import au.com.lifebio.lifebioperson.person.doctor.service.DoctorServiceImpl;
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
public class DoctorServiceTest {

    @Autowired
    private DoctorService doctorService;
    @MockBean
    private DoctorRepository doctorRepository;


    /* Given */
    private Doctor given = new DoctorImpl();
    private String firstName = RandomStringUtils.random(10);
    private String surname = RandomStringUtils.random(10);
    private String middleName = RandomStringUtils.random(10);
    private String nickName = RandomStringUtils.random(10);
    private String identificationNumber = RandomStringUtils.random(10);
    private Doctor.Gender gender = Doctor.Gender.MALE;
    private Doctor.Title title = Doctor.Title.CAPT;
    private String practiseNumber = RandomStringUtils.random(10);
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
        given.setPractiseNumber(practiseNumber);
        given.setLastModified(lastModified);

        Optional<Doctor> optional = Optional.of(given);
        Optional<DoctorImpl> optionalImpl = Optional.of((DoctorImpl) given);

        /* Add and Change */
        Mockito.when(doctorRepository.save((DoctorImpl)given)).thenReturn((DoctorImpl) given);

        /* Find By ID */
        Mockito.when(doctorRepository.findById(given.getOID())).thenReturn(optionalImpl);

        /* Find All */
        DoctorImpl [] list = {new DoctorImpl(), new DoctorImpl(), new DoctorImpl(), new DoctorImpl(), new DoctorImpl()};
        Mockito.when(doctorRepository.findAll()).thenReturn(Arrays.asList(list));

        /* Find Like */
        Set<Doctor> set = Arrays.stream(list).limit(3).collect(Collectors.toSet());
        set.forEach(doctor -> doctor.setSurname(like));
        Mockito.when(doctorRepository.findBySurnameLike(like)).thenReturn(set);

        /* Find By Identification Number */
        Mockito.when(doctorRepository.findByPractiseNumber(given.getPractiseNumber())).thenReturn(optional);

    }

    @Test
    public void whenAdd_thenDoctorShouldBeFound() {
        Optional<Doctor> added = doctorService.addDoctor(doctorRepository.save((DoctorImpl) given));

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getIdentificationNumber()).isEqualTo(identificationNumber);
    }

    @Test
    public void whenChange_thenChangedDoctorShouldBeFound() {
        Optional<Doctor> added = doctorService.addDoctor(doctorRepository.save((DoctorImpl) given));

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getIdentificationNumber()).isEqualTo(identificationNumber);

        added.get().setFirstName(RandomStringUtils.random(10));
        added.get().setSurname(RandomStringUtils.random(10));
        added.get().setMiddleName(RandomStringUtils.random(10));
        added.get().setNickName(RandomStringUtils.random(10));
        added.get().setIdentificationNumber(RandomStringUtils.random(10));
        added.get().setGender(Doctor.Gender.FEMALE);
        added.get().setTitle(Doctor.Title.MR);
        added.get().setPractiseNumber(RandomStringUtils.random(10));

        Optional<Doctor> changedOptional = doctorService.changeDoctor(added.get());

        /* Check Was Changed */
        assertThat(changedOptional.isPresent());
        assertThat(changedOptional.get().getFirstName()).isNotEqualTo(firstName);
        assertThat(changedOptional.get().getSurname()).isNotEqualTo(surname);
        assertThat(changedOptional.get().getMiddleName()).isNotEqualTo(middleName);
        assertThat(changedOptional.get().getNickName()).isNotEqualTo(nickName);
        assertThat(changedOptional.get().getIdentificationNumber()).isNotEqualTo(identificationNumber);
        assertThat(changedOptional.get().getGender()).isNotEqualTo(gender);
        assertThat(changedOptional.get().getTitle()).isNotEqualTo(title);
        assertThat(changedOptional.get().getPractiseNumber()).isNotEqualTo(practiseNumber);

    }

    @Test
    public void whenValidOID_thenDoctorShouldBeFound() {
        Long oID = doctorRepository.save((DoctorImpl) given).getOID();
        Optional<Doctor> found = doctorService.findDoctor(oID);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

    @Test
    public void whenValidIdentificationNumber_thenDoctorShouldBeFound() {
        Optional<Doctor> found = doctorService.findDoctorByPractiseNumber(practiseNumber);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getPractiseNumber()).isEqualTo(practiseNumber);
    }

    @Test
    public void whenFindAll_thenAllShouldBeFound() {
        Optional<Set<Doctor>> optional = doctorService.findAll();

        /* Check */
        assertThat(optional.isPresent());
        assertThat(optional.get().size()).isEqualTo(5);
    }

    @Test
    public void whenFindBySurnameLike_thenOnlyLikeSurnameShouldBeFound() {
        Optional<Set<Doctor>> optional = doctorService.findBySurnameLike(like);

        /* Check */
        assertThat(optional.isPresent());
        assertThat(optional.get().size()).isEqualTo(3);
    }

    @Test
    public void whenDoctorDeleted_thenShouldNotBeFound() {
        Optional<Doctor> found = Optional.of(doctorRepository.save((DoctorImpl) given));
        assertThat(found.isPresent());
        doctorService.deleteDoctor(found.get());
        Mockito.verify(doctorRepository).delete((DoctorImpl) found.get());
    }

    @TestConfiguration
    static class DoctorServiceImplTestContextConfiguration {

        @Bean
        public DoctorService doctorService() {
            return new DoctorServiceImpl();
        }
    }

}
