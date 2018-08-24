package au.com.lifebio.lifebiocontactdetails.contact.controller;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebiocommon.common.exception.ResourceNotFoundException;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumber;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumberImpl;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsContactNumberController.CONTACT_NUMBER_URL;


/**
 * Created by Trevor on 2018/07/10.
 */
@RestController
@RequestMapping(value = CONTACT_NUMBER_URL)
public class ContactDetailsContactNumberControllerImpl implements ContactDetailsContactNumberController {

    @Autowired
    ContactNumberService contactNumberService;

    @Override
    @PostMapping(value = CONTACT_DETAILS_OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addContactDetailsContactNumber(@PathVariable Long contactDetailsOID,
                                                                 @RequestBody ContactNumber contactNumber) {
        if(contactDetailsOID == null){
            throw new IllegalArgumentException("Cannot add contact number, a valid contact details oid must be " +
                    "specified!");
        }
        if(contactNumber == null){
            throw new IllegalArgumentException("Cannot add contact number, a valid contact number must be specified!");
        }
        ContactNumber addedContactNumber = contactNumberService.addContactDetailsContactNumber(contactDetailsOID,
                contactNumber).orElseThrow( () -> new CreationException("Unable to add contact number!"));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + contactDetailsOID + "/" +
                addedContactNumber.getOID()) .buildAndExpand( addedContactNumber.getOID()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @PutMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> changeContactDetailsContactNumber(@PathVariable() Long oID,
                                                       @RequestBody ContactNumber contactNumber) {
        if(oID == null || contactNumber == null){
            throw new IllegalArgumentException("Cannot change contact number, a valid oid and contact number must " +
                    "be specified!");
        }
        Optional<ContactNumber> contactNumberOptional = contactNumberService.findContactDetailsContactNumber(oID);

        contactNumberOptional.orElseThrow(() -> new ResourceNotFoundException("Cannot change contact number," +
                "cannot find contact number!"));

        contactNumber.setOID(oID);

        contactNumberService.changeContactDetailsContactNumber(contactNumber);

        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactNumber findContactDetailsContactNumber(@PathVariable Long oID) {
        if(oID == null || oID < 0){
            throw new IllegalArgumentException("Cannot find contact number, a valid oid must be specified!");
        }
        Optional<ContactNumber> contactNumber = contactNumberService.findContactDetailsContactNumber(oID);

        return contactNumber.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find the contact number with the specified oid!"));
    }

    @Override
    @GetMapping(value = "all/" + CONTACT_DETAILS_OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContactNumberImpl> findAllContactDetailsContactNumbers(@PathVariable  Long contactDetailsOID) {
        if(contactDetailsOID == null || contactDetailsOID < 1){
            throw new IllegalArgumentException("Cannot find contact number list, valid contact details oid must be " +
                    "specified!");
        }
        Optional<List<ContactNumberImpl>> contactNumbers = contactNumberService.findAllContactDetailsContactNumbers(
                contactDetailsOID);

        return contactNumbers.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find the contact numbers with the specified oid!"));
    }

    @Override
    @DeleteMapping(value = CONTACT_DETAILS_OID_PATH_VARIABLE + OID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteContactDetailsContactNumber(@PathVariable Long contactDetailsOID, @PathVariable Long oID) {
        if(contactDetailsOID == null || oID == null){
            throw new IllegalArgumentException("Cannot delete contact number, a valid contact details oid and oid must " +
                    "be specified!");
        }
        contactNumberService.deleteContactDetailsContactNumber(contactDetailsOID, contactNumberService
                .findContactDetailsContactNumber(oID).orElseThrow(() -> new ResourceNotFoundException(
                        "Cannot delete the contact number with the oid of specified!")));
    }
}
