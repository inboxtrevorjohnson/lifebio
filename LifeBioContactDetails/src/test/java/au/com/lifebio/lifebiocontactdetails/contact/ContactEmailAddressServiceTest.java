package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactEmailAddressRepository;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddressImpl;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetailsImpl;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactEmailAddressService;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactEmailAddressServiceImpl;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Trevor on 2018/07/10.
 */
@RunWith(SpringRunner.class)
public class ContactEmailAddressServiceTest {

    @Autowired
    private ContactEmailAddressService contactEmailAddressService;

    @MockBean
    private ContactDetailsRepository contactDetailsRepository;

    @MockBean
    private ContactEmailAddressRepository contactEmailAddressRepository;

    private LocalDateTime lastModified = LocalDateTime.now();
    private long oid = 0;
    private Long [] oids = {++oid, ++oid, ++oid, ++oid, ++oid};
    List<ContactEmailAddressImpl> asList;



    /* Given */
    private ContactDetails givenContactDetails = new ContactDetailsImpl();

    private ContactEmailAddress givenContactEmailAddress = new ContactEmailAddressImpl();

    @Before
    public void setUp() {

        /* Given*/
        givenContactDetails.setOID(1L);
        givenContactDetails.setLastModified(lastModified);
        givenContactEmailAddress.setOID(1L);
        givenContactEmailAddress.setLastModified(lastModified);

        Optional<ContactDetailsImpl> optionalContactDetails = Optional.of((ContactDetailsImpl) givenContactDetails);

        Optional<ContactEmailAddressImpl> optionalContactEmailAddress = Optional.of(
                (ContactEmailAddressImpl) givenContactEmailAddress);

        /* Add and Change */
        Mockito.when(contactDetailsRepository.save((ContactDetailsImpl) givenContactDetails)).thenReturn(
                (ContactDetailsImpl) givenContactDetails);
        Mockito.when(contactEmailAddressRepository.save((ContactEmailAddressImpl) givenContactEmailAddress)).thenReturn(
                (ContactEmailAddressImpl) givenContactEmailAddress);

        /* Find By ID */
        Mockito.when(contactDetailsRepository.findById(givenContactDetails.getOID())).thenReturn(optionalContactDetails);

        /* Find By ID */
        Mockito.when(contactEmailAddressRepository.findById(givenContactEmailAddress.getOID())).thenReturn(
                optionalContactEmailAddress);

        /* Find All For Parent */
        ContactEmailAddressImpl [] contactEmailAddressesList = {new ContactEmailAddressImpl(),
                new ContactEmailAddressImpl(), new ContactEmailAddressImpl(), new ContactEmailAddressImpl(),
                new ContactEmailAddressImpl()};
        long oID = 0;
        for (ContactEmailAddressImpl contactEmailAddress : contactEmailAddressesList) {
            contactEmailAddress.setOID(++oID);
        }
        givenContactDetails.setContactEmailAddresses(Arrays.asList(contactEmailAddressesList));

    }

    @Test
    public void whenAdd_thenContactEmailAddressShouldBeFound() {
        Optional<ContactEmailAddress> added = contactEmailAddressService.addContactDetailsContactEmailAddress(
                givenContactDetails.getOID(), givenContactEmailAddress);

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getLastModified()).isNotEqualTo(lastModified);
    }

    @Test
    public void whenChange_thenChangedContactDetailsShouldBeFound() {

        Optional<ContactEmailAddress> changedOptional = contactEmailAddressService
                .changeContactDetailsContactEmailAddress(givenContactEmailAddress);

        /* Check Was Changed */
        assertThat(changedOptional.isPresent());
        assertThat(changedOptional.get().getLastModified()).isNotEqualTo(lastModified);
    }

    @Test
    public void whenValidOID_thenContactEmailAddressShouldBeFound() {
        Long oID = contactEmailAddressRepository.save((ContactEmailAddressImpl) givenContactEmailAddress).getOID();
        Optional<ContactEmailAddress> found = contactEmailAddressService.findContactDetailsContactEmailAddress(oID);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

    @Test
    public void whenValidContactDetailsOID_thenContactEmailAddressShouldBeFound() {
        Long oID = contactEmailAddressRepository.save((ContactEmailAddressImpl) givenContactEmailAddress).getOID();
        Optional<List<ContactEmailAddressImpl>> foundEmailAddresses = contactEmailAddressService
                .findAllContactDetailsContactEmailAddresses(givenContactDetails.getOID());

        /* Check */
        assertThat(foundEmailAddresses.isPresent());
        assertThat(foundEmailAddresses.get().size() == 5);
    }

    @Test
    public void whenContactEmailAddressDeleted_thenShouldNotBeFound() {
        contactEmailAddressService.deleteContactDetailsContactEmailAddress(givenContactDetails.getOID(),
                givenContactEmailAddress);
        Mockito.verify(contactDetailsRepository).save((ContactDetailsImpl) givenContactDetails);
    }

    @TestConfiguration
    static class ContactEmailAddressServiceImplTestContextConfiguration {

        @Bean
        public ContactDetailsService contactDetailsService() {
            return new ContactDetailsServiceImpl();
        }

        @Bean
        public ContactEmailAddressService contactEmailAddressService() {
            return new ContactEmailAddressServiceImpl();
        }

        @Bean
        public ContactEmailAddress contactEmailAddress(){
            return new ContactEmailAddressImpl();
        }
    }


}
