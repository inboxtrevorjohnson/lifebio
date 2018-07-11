package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactNumberRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetailsImpl;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumber;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumberImpl;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactDetailsService;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactDetailsServiceImpl;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactNumberService;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactNumberServiceImpl;
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
 * Created by Trevor on 2018/07/09.
 */
@RunWith(SpringRunner.class)
public class ContactNumberServiceTest {

    @Autowired
    private ContactNumberService contactNumberService;

    @MockBean
    private ContactDetailsRepository contactDetailsRepository;

    @MockBean
    private ContactNumberRepository contactNumberRepository;

    private LocalDateTime lastModified = LocalDateTime.now();
    private long oid = 0;
    private Long [] oids = {++oid, ++oid, ++oid, ++oid, ++oid};
    List<ContactNumberImpl> asList;



    /* Given */
    private ContactDetails givenContactDetails = new ContactDetailsImpl();

    private ContactNumber givenContactNumber = new ContactNumberImpl();

    @Before
    public void setUp() {

        /* Given*/
        givenContactDetails.setOID(1L);
        givenContactDetails.setLastModified(lastModified);
        givenContactNumber.setOID(2L);
        givenContactNumber.setLastModified(lastModified);

        Optional<ContactDetailsImpl> optionalContactDetails = Optional.of((ContactDetailsImpl) givenContactDetails);

        Optional<ContactNumberImpl> optionalContactNumber = Optional.of((ContactNumberImpl) givenContactNumber);

        /* Add and Change */
        Mockito.when(contactDetailsRepository.save((ContactDetailsImpl) givenContactDetails)).thenReturn(
                (ContactDetailsImpl) givenContactDetails);
        Mockito.when(contactNumberRepository.save((ContactNumberImpl) givenContactNumber)).thenReturn(
                (ContactNumberImpl) givenContactNumber);

        /* Find By ID */
        Mockito.when(contactDetailsRepository.findById(givenContactDetails.getOID())).thenReturn(optionalContactDetails);

        Mockito.when(contactNumberRepository.findById(givenContactNumber.getOID())).thenReturn(optionalContactNumber);

        /* Find All For Parent */
        ContactNumberImpl [] contactNumbersList = {new ContactNumberImpl(), new ContactNumberImpl(),
                new ContactNumberImpl(), new ContactNumberImpl(), new ContactNumberImpl()};
        long oID = 0;
        for (ContactNumberImpl contactNumber : contactNumbersList) {
            contactNumber.setOID(++oID);
        }
        givenContactDetails.setContactNumbers(Arrays.asList(contactNumbersList));
    }

    @Test
    public void whenAdd_thenContactNumberShouldBeFound() {
        Optional<ContactNumber> added = contactNumberService.addContactDetailsContactNumber(
                givenContactDetails.getOID(), givenContactNumber);

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getLastModified()).isNotEqualTo(lastModified);
    }

    @Test
    public void whenChange_thenChangedContactDetailsShouldBeFound() {

        Optional<ContactNumber> changedOptional = contactNumberService.changeContactDetailsContactNumber(
                givenContactNumber);

        /* Check Was Changed */
        assertThat(changedOptional.isPresent());
        assertThat(changedOptional.get().getLastModified()).isNotEqualTo(lastModified);
    }

    @Test
    public void whenValidOID_thenContactNumberShouldBeFound() {
        Long oID = contactNumberRepository.save((ContactNumberImpl) givenContactNumber).getOID();
        Optional<ContactNumber> found = contactNumberService.findContactDetailsContactNumber(oID);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

    @Test
    public void whenValidContactDetailsOID_thenContactNumbersShouldBeFound() {
        Long oID = contactNumberRepository.save((ContactNumberImpl) givenContactNumber).getOID();
        Optional<List<ContactNumberImpl>> foundContactNumbers = contactNumberService.findAllContactDetailsContactNumbers(
                givenContactDetails.getOID());

        /* Check */
        assertThat(foundContactNumbers.isPresent());
        assertThat(foundContactNumbers.get().size() == 5);
    }

    @Test
    public void whenContactNumberDeleted_thenShouldNotBeFound() {
        contactNumberService.deleteContactDetailsContactNumber(givenContactDetails.getOID(), givenContactNumber);
        Mockito.verify(contactDetailsRepository).save((ContactDetailsImpl) givenContactDetails);
    }

    @TestConfiguration
    static class ContactNumberServiceImplTestContextConfiguration {

        @Bean
        public ContactDetailsService contactDetailsService() {
            return new ContactDetailsServiceImpl();
        }

        @Bean
        public ContactNumberService contactNumberService() {
            return new ContactNumberServiceImpl();
        }

        @Bean
        public ContactNumber contactNumber(){
            return new ContactNumberImpl();
        }
    }


}
