package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactAddressRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.*;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactDetailsService;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactDetailsServiceImpl;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactAddressService;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactAddressServiceImpl;
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
public class ContactAddressServiceTest {

    @Autowired
    private ContactAddressService contactAddressService;

    @MockBean
    private ContactDetailsRepository contactDetailsRepository;

    @MockBean
    private ContactAddressRepository contactAddressRepository;

    private LocalDateTime lastModified = LocalDateTime.now();
    private long oid = 0;
    private Long [] oids = {++oid, ++oid, ++oid, ++oid, ++oid};
    List<ContactAddressImpl> asList;



    /* Given */
    private ContactDetails givenContactDetails = new ContactDetailsImpl();

    private ContactAddress givenContactAddress = new ContactAddressImpl();

    @Before
    public void setUp() {

        /* Given*/
        givenContactDetails.setOID(1L);
        givenContactDetails.setLastModified(lastModified);
        givenContactAddress.setOID(1L);
        givenContactAddress.setLastModified(lastModified);

        Optional<ContactDetailsImpl> optionalContactDetails = Optional.of((ContactDetailsImpl) givenContactDetails);

        Optional<ContactAddressImpl> optionalContactAddress = Optional.of((ContactAddressImpl) givenContactAddress);

        /* Add and Change */
        Mockito.when(contactDetailsRepository.save((ContactDetailsImpl) givenContactDetails)).thenReturn(
                (ContactDetailsImpl) givenContactDetails);
        Mockito.when(contactAddressRepository.save((ContactAddressImpl) givenContactAddress)).thenReturn(
                (ContactAddressImpl) givenContactAddress);

        /* Find By ID */
        Mockito.when(contactDetailsRepository.findById(givenContactDetails.getOID())).thenReturn(optionalContactDetails);

        /* Find By ID */
        Mockito.when(contactAddressRepository.findById(givenContactAddress.getOID())).thenReturn(optionalContactAddress);

        /* Find All For Parent */
        ContactAddressImpl [] contactAddressesList = {new ContactAddressImpl(), new ContactAddressImpl(),
                new ContactAddressImpl(), new ContactAddressImpl(), new ContactAddressImpl()};
        long oID = 0;
        for (ContactAddressImpl contactAddress : contactAddressesList) {
            contactAddress.setOID(++oID);
        }
        givenContactDetails.setContactAddresses(Arrays.asList(contactAddressesList));

    }

    @Test
    public void whenAdd_thenContactAddressShouldBeFound() {
        Optional<ContactAddress> added = contactAddressService.addContactDetailsContactAddress(
                givenContactDetails.getOID(), givenContactAddress);

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getLastModified()).isNotEqualTo(lastModified);
    }

    @Test
    public void whenChange_thenChangedContactDetailsShouldBeFound() {

        Optional<ContactAddress> changedOptional = contactAddressService.changeContactDetailsContactAddress(
                givenContactAddress);

        /* Check Was Changed */
        assertThat(changedOptional.isPresent());
        assertThat(changedOptional.get().getLastModified()).isNotEqualTo(lastModified);
    }

    @Test
    public void whenValidOID_thenContactAddressShouldBeFound() {
        Long oID = contactAddressRepository.save((ContactAddressImpl) givenContactAddress).getOID();
        Optional<ContactAddress> found = contactAddressService.findContactDetailsContactAddress(oID);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

    @Test
    public void whenValidContactDetailsOID_thenContactAddressShouldBeFound() {
        Long oID = contactAddressRepository.save((ContactAddressImpl) givenContactAddress).getOID();
        Optional<List<ContactAddressImpl>> foundAddresses = contactAddressService
                .findAllContactDetailsContactAddresses(givenContactDetails.getOID());

        /* Check */
        assertThat(foundAddresses.isPresent());
        assertThat(foundAddresses.get().size() == 5);
    }

    @Test
    public void whenContactAddressDeleted_thenShouldNotBeFound() {
        contactAddressService.deleteContactDetailsContactAddress(givenContactDetails.getOID(), givenContactAddress);
        Mockito.verify(contactDetailsRepository).save((ContactDetailsImpl) givenContactDetails);
    }

    @TestConfiguration
    static class ContactAddressServiceImplTestContextConfiguration {

        @Bean
        public ContactDetailsService contactDetailsService() {
            return new ContactDetailsServiceImpl();
        }

        @Bean
        public ContactAddressService contactAddressService() {
            return new ContactAddressServiceImpl();
        }

        @Bean
        public ContactAddress contactAddress(){
            return new ContactAddressImpl();
        }
    }


}
