package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetailsImpl;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumber;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumberImpl;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactDetailsService;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactDetailsServiceImpl;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Trevor on 2018/06/26.
 */
@RunWith(SpringRunner.class)
public class ContactDetailsServiceTest {

    @Autowired
    private ContactDetailsService contactDetailsService;
    @MockBean
    private ContactDetailsRepository contactDetailsRepository;

    private LocalDateTime lastModified = LocalDateTime.now();
    private long oid = 0;
    private Long [] oids = {++oid, ++oid, ++oid, ++oid, ++oid};
    List<ContactDetailsImpl> asList;



    /* Given */
    private ContactDetails givenContactDetails = new ContactDetailsImpl();

    private ContactNumber givenContactNumber = new ContactNumberImpl();

    @Before
    public void setUp() {
        /* Contact Details*/
        givenContactDetails.setOID(1L);
        givenContactDetails.setLastModified(lastModified);

        Optional<ContactDetails> optional = Optional.of(givenContactDetails);

        Optional<ContactDetailsImpl> optionalImpl = Optional.of((ContactDetailsImpl) givenContactDetails);

        /* Add and Change */
        Mockito.when(contactDetailsRepository.save((ContactDetailsImpl) givenContactDetails)).thenReturn(
                (ContactDetailsImpl) givenContactDetails);

        /* Find By ID */
        Mockito.when(contactDetailsRepository.findById(givenContactDetails.getOID())).thenReturn(optionalImpl);

        /* Find All */
        ContactDetailsImpl [] list = {new ContactDetailsImpl(), new ContactDetailsImpl(), new ContactDetailsImpl(),
                new ContactDetailsImpl(), new ContactDetailsImpl()};
        Mockito.when(contactDetailsRepository.findAll()).thenReturn(Arrays.asList(list));

        /* Find All By ID */
        asList = Arrays.stream(list).collect(Collectors.toList());
        for(int i = 0; i < asList.size(); i++){
            asList.get(i).setOID(oids[i]);
        }
        Mockito.when(contactDetailsRepository.findAllById(Arrays.stream(oids).collect(Collectors.toSet())))
                .thenReturn(asList);
    }

    @Test
    public void whenAdd_thenContactDetailsShouldBeFound() {
        Optional<ContactDetails> added = contactDetailsService.addContactDetails(contactDetailsRepository.save(
                (ContactDetailsImpl) givenContactDetails));

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getLastModified()).isNotEqualTo(lastModified);
    }

    @Test
    public void whenChange_thenChangedContactDetailsShouldBeFound() {
        Optional<ContactDetails> changed = contactDetailsService.addContactDetails(contactDetailsRepository.save(
                (ContactDetailsImpl) givenContactDetails));

        /* Check */
        assertThat(changed.isPresent());
        assertThat(changed.get().getLastModified()).isNotEqualTo(lastModified);

        Optional<ContactDetails> changedOptional = contactDetailsService.changeContactDetails(changed.get());

        /* Check Was Changed */
        assertThat(changedOptional.isPresent());
        assertThat(changedOptional.get().getLastModified()).isNotEqualTo(lastModified);
    }

    @Test
    public void whenValidOID_thenContactDetailsShouldBeFound() {
        Long oID = contactDetailsRepository.save((ContactDetailsImpl) givenContactDetails).getOID();
        Optional<ContactDetails> found = contactDetailsService.findContactDetails(oID);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

    @Test
    public void whenFindAll_thenAllShouldBeFound() {
        Optional<Set<ContactDetails>> optional = contactDetailsService.findAll();

        /* Check */
        assertThat(optional.isPresent());
        assertThat(optional.get().size()).isEqualTo(5);
    }

    @Test
    public void whenFindByAllByOID_thenOnlyContactDetailsWithOIDShouldBeFound() {
        Optional<Set<ContactDetails>> optional = contactDetailsService.findAllContactDetailsByOID(
                Arrays.stream(oids).collect(Collectors.toSet()));

        /* Check */
        assertThat(optional.isPresent());
        assertThat(optional.get().size()).isEqualTo(5);
    }

    @Test
    public void whenContactDetailsDeleted_thenShouldNotBeFound() {
        Optional<ContactDetailsImpl> found = Optional.of(contactDetailsRepository.save((ContactDetailsImpl) givenContactDetails));
        assertThat(found.isPresent());
        contactDetailsService.deleteContactDetails(found.get());
        Mockito.verify(contactDetailsRepository).delete(found.get());
    }

    @TestConfiguration
    static class ContactDetailsServiceImplTestContextConfiguration {

        @Bean
        public ContactDetailsService contactDetailsService() {
            return new ContactDetailsServiceImpl();
        }

        @Bean
        public ContactDetails contactDetails(){
            return new ContactDetailsImpl();
        }
    }


}
