package au.com.lifebio.lifebiocontactdetails.contact.controller;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebiocommon.common.exception.ResourceNotFoundException;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddressImpl;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsContactAddressController.CONTACT_ADDRESS_URL;


/**
 * Created by Trevor on 2018/07/11.
 */
@RestController
@RequestMapping(value = CONTACT_ADDRESS_URL)
public class ContactDetailsContactAddressControllerImpl implements ContactDetailsContactAddressController {

    @Autowired
    ContactAddressService contactAddressService;

    @Override
    @PostMapping(value = CONTACT_DETAILS_OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addContactDetailsContactAddress(@PathVariable Long contactDetailsOID,
                                                                 @RequestBody ContactAddress contactAddress) {
        if(contactDetailsOID == null){
            throw new IllegalArgumentException("Cannot add contact address, a valid contact details oid must be " +
                    "specified!");
        }
        if(contactAddress == null){
            throw new IllegalArgumentException("Cannot add contact address, a valid contact address must be specified!");
        }
        ContactAddress addedContactAddress = contactAddressService.addContactDetailsContactAddress(contactDetailsOID,
                contactAddress).orElseThrow( () -> new CreationException("Unable to add contact address!"));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + contactDetailsOID + "/" +
                addedContactAddress.getOID()) .buildAndExpand( addedContactAddress.getOID()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @PutMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> changeContactDetailsContactAddress(@PathVariable() Long oID,
                                                       @RequestBody ContactAddress contactAddress) {
        if(oID == null || contactAddress == null){
            throw new IllegalArgumentException("Cannot change contact address, a valid oid and contact address must " +
                    "be specified!");
        }
        Optional<ContactAddress> contactAddressOptional = contactAddressService.findContactDetailsContactAddress(oID);

        contactAddressOptional.orElseThrow(() -> new ResourceNotFoundException("Cannot change contact address," +
                "cannot find contact address!"));

        contactAddress.setOID(oID);

        contactAddressService.changeContactDetailsContactAddress(contactAddress);

        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactAddress findContactDetailsContactAddress(@PathVariable Long oID) {
        if(oID == null || oID < 0){
            throw new IllegalArgumentException("Cannot find contact address, a valid oid must be specified!");
        }
        Optional<ContactAddress> contactAddress = contactAddressService.findContactDetailsContactAddress(oID);

        return contactAddress.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find the contact address with the specified oid!"));
    }

    @Override
    @GetMapping(value = "all/" + CONTACT_DETAILS_OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContactAddressImpl> findAllContactDetailsContactAddresses(@PathVariable  Long contactDetailsOID) {
        if(contactDetailsOID == null || contactDetailsOID < 1){
            throw new IllegalArgumentException("Cannot find contact address list, valid contact details oid must be " +
                    "specified!");
        }
        Optional<List<ContactAddressImpl>> contactAddresss = contactAddressService.findAllContactDetailsContactAddresses(
                contactDetailsOID);

        return contactAddresss.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find the contact addresss with the specified oid!"));
    }

    @Override
    @DeleteMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteContactDetailsContactAddress(@PathVariable Long contactDetailsOID, @PathVariable Long oID) {
        if(contactDetailsOID == null || oID == null){
            throw new IllegalArgumentException("Cannot delete contact address, a valid contact details oid and oid " +
                    "must be specified!");
        }
        contactAddressService.deleteContactDetailsContactAddress(contactDetailsOID, contactAddressService
                .findContactDetailsContactAddress(oID).orElseThrow(() -> new ResourceNotFoundException(
                        "Cannot delete the contact address with the oid of specified!")));
    }
}
