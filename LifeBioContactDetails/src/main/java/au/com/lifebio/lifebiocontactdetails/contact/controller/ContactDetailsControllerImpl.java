package au.com.lifebio.lifebiocontactdetails.contact.controller;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebiocommon.common.exception.ResourceNotFoundException;
import au.com.lifebio.lifebiocontactdetails.contact.model.*;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactDetailsService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsController.CONTACT_DETAILS_URL;

/**
 * Created by Trevor on 2018/07/05.
 */
@RestController
@RequestMapping(value = CONTACT_DETAILS_URL)
public class ContactDetailsControllerImpl implements ContactDetailsController {

    @Autowired
    ContactDetailsService contactDetailsService;

    /* Set up test data */
    List<Optional<ContactDetails>> contactDetailsList = new ArrayList<>();

    @PostConstruct
    public void postConstruct(){
        for(int i = 0; i < 10; i++){
            ContactDetails contactDetails = new ContactDetailsImpl();
            Optional<ContactDetails> optional = contactDetailsService.addContactDetails(contactDetails);
        }
        contactDetailsList.stream().forEach( contactDetails -> {
            for(int i = 0; i < 10; i++){
                ContactNumber contactNumber = new ContactNumberImpl();
                contactNumber.setNumber(RandomStringUtils.randomNumeric(10));
                contactNumber.setContactType(ContactType.BUSINESS);

                ContactEmailAddress contactEmailAddress = new ContactEmailAddressImpl();
                contactEmailAddress.setEmailAddress( RandomStringUtils.randomAlphabetic(5) + "@" + RandomStringUtils
                        .randomAlphabetic(5) + "." + RandomStringUtils.randomAlphabetic(3));
                contactEmailAddress.setContactType(ContactType.BUSINESS);

                ContactAddress contactAddress = new ContactAddressImpl();
                contactAddress.setLine1( RandomStringUtils.randomAlphanumeric(10));
                contactAddress.setLine2( RandomStringUtils.randomAlphanumeric(10));
                contactAddress.setLine3( RandomStringUtils.randomAlphanumeric(10));
                contactAddress.setCityArea( RandomStringUtils.randomAlphanumeric(10));
                contactAddress.setState( RandomStringUtils.randomAlphanumeric(10));
                contactAddress.setPostalCode( RandomStringUtils.randomAlphanumeric(10));
                contactAddress.setCountry( RandomStringUtils.randomAlphanumeric(10));
                contactAddress.setContactType(ContactType.BUSINESS);

                contactDetails.get().getContactNumbers().add((ContactNumberImpl) contactNumber);
                contactDetails.get().getContactEmailAddresses().add((ContactEmailAddressImpl) contactEmailAddress);
                contactDetails.get().getContactAddresses().add((ContactAddressImpl) contactAddress);
                contactDetailsService.changeContactDetails(contactDetails.get());
            }
        });

    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addContactDetails(@RequestBody ContactDetails contactDetails) {
        if(contactDetails == null){
            throw new IllegalArgumentException("Cannot add contact details, a valid contact details must be " +
                    "specified!");
        }
        ContactDetails addedContactDetails = contactDetailsService.addContactDetails(contactDetails).orElseThrow(
                () -> new CreationException("Unable to add contact details!"));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(OID_PATH_VARIABLE) .buildAndExpand(
                addedContactDetails.getOID()).toUri();

        return ResponseEntity.created(location).header(CONTACT_DETAILS_OID, addedContactDetails.getOID().toString()).build();
    }

    @Override
    @PutMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> changeContactDetails(@PathVariable Long oID,
                                                       @RequestBody ContactDetails contactDetails) {
        if(oID == null || contactDetails == null){
            throw new IllegalArgumentException("Cannot change contact details, a valid oid and contact details must " +
                    "be specified!");
        }
        Optional<ContactDetails> contactDetailsOptional = contactDetailsService.findContactDetails(oID);

        contactDetailsOptional.orElseThrow(() -> new ResourceNotFoundException("Cannot change contact details," +
                "cannot find contact details!"));

        contactDetails.setOID(oID);

        contactDetailsService.changeContactDetails(contactDetails);

        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactDetails findContactDetails(@PathVariable Long oID) {
        if(oID == null || oID < 0){
            throw new IllegalArgumentException("Cannot find contact details, a valid oid must be specified!");
        }
        Optional<ContactDetails> contactDetails = contactDetailsService.findContactDetails(oID);

        return contactDetails.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find the contact details with the specified oid!"));
    }

    @Override
    @GetMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ContactDetails> findAllContactDetails() {
        Optional<Set<ContactDetails>> contactDetails = contactDetailsService.findAll();

        return contactDetails.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find contact details!"));
    }

    @Override
    @DeleteMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteContactDetails(@PathVariable Long oID) {
        if(oID == null){
            throw new IllegalArgumentException("Cannot delete contact details, a valid oid must be specified!");
        }
        contactDetailsService.deleteContactDetails(contactDetailsService.findContactDetails(oID).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Cannot delete the contact details with the oid of specified!")));
    }
}
