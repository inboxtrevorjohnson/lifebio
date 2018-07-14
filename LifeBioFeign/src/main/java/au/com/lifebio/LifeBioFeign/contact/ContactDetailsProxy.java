package au.com.lifebio.LifeBioFeign.contact;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumber;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsContactAddressController.CONTACT_ADDRESS_URL;
import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsContactEmailAddressController.CONTACT_EMAIL_ADDRESS_URL;
import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsContactNumberController.CONTACT_NUMBER_URL;
import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsController.CONTACT_DETAILS_URL;
import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsController.OID_PATH_VARIABLE;
import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactMeansController.CONTACT_DETAILS_OID_PATH_VARIABLE;

/**
 * Created by Trevor on 2018/07/12.
 */
@FeignClient(name = "LifeBioContactDetails")
@RibbonClient(name = "LifeBioContactDetails")
public interface ContactDetailsProxy {

        @PostMapping(value = CONTACT_DETAILS_URL,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object> addContactDetails(@RequestBody ContactDetails contactDetails);

        @PutMapping(value = CONTACT_DETAILS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object>  changeContactDetails(@PathVariable(value = "oID") Long oID,
                                                     @RequestBody ContactDetails contactDetails);

        @GetMapping(value = CONTACT_DETAILS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ContactDetails findContactDetails(@PathVariable(value = "oID") Long oID);

        @GetMapping(value = CONTACT_DETAILS_URL + "/all",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        Set<ContactDetails> findAllContactDetails();

        @DeleteMapping(value = CONTACT_DETAILS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
        void deleteContactDetails(@PathVariable(value = "oID") Long oID);

        /* Contact Number API */

        @PostMapping(value = CONTACT_NUMBER_URL + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object> addContactDetailsContactNumber(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                @RequestBody ContactNumber contactNumber);

        @PutMapping(value = CONTACT_NUMBER_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object>  changeContactDetailsContactNumber(@PathVariable(value = "oID") Long oID,
                                                     @RequestBody ContactNumber contactNumber);

        @GetMapping(value = CONTACT_NUMBER_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ContactNumber findContactDetailsContactNumber(@PathVariable(value = "oID") Long oID);

        @GetMapping(value = CONTACT_NUMBER_URL + "/all" + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        Set<ContactNumber> findAllContactDetailsContactNumbers(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID);

        @DeleteMapping(value = CONTACT_NUMBER_URL + CONTACT_DETAILS_OID_PATH_VARIABLE + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
        void deleteContactDetailsContactNumber(@PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                @PathVariable(value = "oID") Long oID);

        /* Contact Address API */

        @PostMapping(value = CONTACT_ADDRESS_URL + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object> addContactDetailsContactAddress(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                @RequestBody ContactAddress contactAddress);

        @PutMapping(value = CONTACT_ADDRESS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object>  changeContactDetailsContactAddress(@PathVariable(value = "oID") Long oID,
                                                     @RequestBody ContactAddress contactAddress);

        @GetMapping(value = CONTACT_ADDRESS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ContactAddress findContactDetailsContactAddress(@PathVariable(value = "oID") Long oID);

        @GetMapping(value = CONTACT_ADDRESS_URL + "/all" + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        Set<ContactAddress> findAllContactDetailsContactAddresses(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID);

        @DeleteMapping(value = CONTACT_ADDRESS_URL + CONTACT_DETAILS_OID_PATH_VARIABLE + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
        void deleteContactDetailsContactAddress(@PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                @PathVariable(value = "oID") Long oID);

        /* Contact Email Address API */

        @PostMapping(value = CONTACT_EMAIL_ADDRESS_URL + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object> addContactDetailsContactEmailAddress(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                @RequestBody ContactEmailAddress contactEmailAddress);

        @PutMapping(value = CONTACT_EMAIL_ADDRESS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object>  changeContactDetailsContactEmailAddress(@PathVariable(value = "oID") Long oID,
                                                     @RequestBody ContactEmailAddress contactEmailAddress);

        @GetMapping(value = CONTACT_EMAIL_ADDRESS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ContactEmailAddress findContactDetailsContactEmailAddress(@PathVariable(value = "oID") Long oID);

        @GetMapping(value = CONTACT_EMAIL_ADDRESS_URL + "/all" + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        Set<ContactEmailAddress> findAllContactDetailsContactEmailAddresses(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID);

        @DeleteMapping(value = CONTACT_EMAIL_ADDRESS_URL + CONTACT_DETAILS_OID_PATH_VARIABLE + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
        void deleteContactDetailsContactEmailAddress(@PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                @PathVariable(value = "oID") Long oID);


}


