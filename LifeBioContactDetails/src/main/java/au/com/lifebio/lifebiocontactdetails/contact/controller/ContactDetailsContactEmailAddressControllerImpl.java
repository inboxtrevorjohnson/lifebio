package au.com.lifebio.lifebiocontactdetails.contact.controller;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebiocommon.common.exception.ResourceNotFoundException;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddressImpl;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactEmailAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsContactEmailAddressController.CONTACT_EMAIL_ADDRESS_URL;


/**
 * Created by Trevor on 2018/07/11.
 */
@RestController
@RequestMapping(value = CONTACT_EMAIL_ADDRESS_URL)
public class ContactDetailsContactEmailAddressControllerImpl implements ContactDetailsContactEmailAddressController {

    @Autowired
    ContactEmailAddressService contactEmailAddressService;

    @Override
    @PostMapping(value = CONTACT_DETAILS_OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addContactDetailsContactEmailAddress(@PathVariable Long contactDetailsOID,
                                                                 @RequestBody ContactEmailAddress contactEmailAddress) {
        if(contactDetailsOID == null){
            throw new IllegalArgumentException("Cannot add contact email address, a valid contact details oid must " +
                    "be specified!");
        }
        if(contactEmailAddress == null){
            throw new IllegalArgumentException("Cannot add contact email address, a valid contact emailAddress must " +
                    "be specified!");
        }
        ContactEmailAddress addedContactEmailAddress = contactEmailAddressService.addContactDetailsContactEmailAddress(
                contactDetailsOID, contactEmailAddress).orElseThrow(
                        () -> new CreationException("Unable to add contact emailAddress!"));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + contactDetailsOID + "/" +
                addedContactEmailAddress.getOID()) .buildAndExpand( addedContactEmailAddress.getOID()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @PutMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> changeContactDetailsContactEmailAddress(@PathVariable() Long oID,
                                                       @RequestBody ContactEmailAddress contactEmailAddress) {
        if(oID == null || contactEmailAddress == null){
            throw new IllegalArgumentException("Cannot change contact email address, a valid oid and contact " +
                    "email address must be specified!");
        }
        Optional<ContactEmailAddress> contactEmailAddressOptional = contactEmailAddressService
                .findContactDetailsContactEmailAddress(oID);

        contactEmailAddressOptional.orElseThrow(() -> new ResourceNotFoundException("Cannot change contact email " +
                "address, cannot find contact emailAddress!"));

        contactEmailAddress.setOID(oID);

        contactEmailAddressService.changeContactDetailsContactEmailAddress(contactEmailAddress);

        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactEmailAddress findContactDetailsContactEmailAddress(@PathVariable Long oID) {
        if(oID == null || oID < 0){
            throw new IllegalArgumentException("Cannot find contact email address, a valid oid must be specified!");
        }
        Optional<ContactEmailAddress> contactEmailAddress = contactEmailAddressService
                .findContactDetailsContactEmailAddress(oID);

        return contactEmailAddress.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find the contact email address with the specified oid!"));
    }

    @Override
    @GetMapping(value = "all/" + CONTACT_DETAILS_OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContactEmailAddressImpl> findAllContactDetailsContactEmailAddresses(
            @PathVariable Long contactDetailsOID) {
        if(contactDetailsOID == null || contactDetailsOID < 1){
            throw new IllegalArgumentException("Cannot find contact emailAddress list, valid contact details oid " +
                    "must be specified!");
        }
        Optional<List<ContactEmailAddressImpl>> contactEmailAddresss = contactEmailAddressService
                .findAllContactDetailsContactEmailAddresses(contactDetailsOID);

        return contactEmailAddresss.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find the contact email addresss with the specified oid!"));
    }

    @Override
    @DeleteMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteContactDetailsContactEmailAddress(@PathVariable Long contactDetailsOID, @PathVariable Long oID) {
        if(contactDetailsOID == null || oID == null){
            throw new IllegalArgumentException("Cannot delete contact email address, a valid contact details oid " +
                    "and oid must be specified!");
        }
        contactEmailAddressService.deleteContactDetailsContactEmailAddress(contactDetailsOID, contactEmailAddressService
                .findContactDetailsContactEmailAddress(oID).orElseThrow(() -> new ResourceNotFoundException(
                        "Cannot delete the contact email address with the oid of specified!")));
    }
}
