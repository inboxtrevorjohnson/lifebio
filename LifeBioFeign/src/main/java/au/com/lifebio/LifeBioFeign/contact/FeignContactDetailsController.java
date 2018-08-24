package au.com.lifebio.LifeBioFeign.contact;

import au.com.lifebio.lifebiocontactdetails.contact.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsController.CONTACT_TYPES_URL;
import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsController.OID_PATH_VARIABLE;
import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactMeansController.CONTACT_DETAILS_OID_PATH_VARIABLE;

@RefreshScope
@RestController
public class FeignContactDetailsController {
        private final String CONTACT_DETAILS_URL = "/dashboard/contactdetails";
        private final String CONTACT_NUMBER_URL = "/dashboard/contactdetails/contactnumber";
        private final String CONTACT_ADDRESS_URL = "/dashboard/contactdetails/contactaddress";
        private final String CONTACT_EMAIL_ADDRESS_URL = "/dashboard/contactdetails/contactemailaddress";

        @Autowired
        ContactDetailsProxy proxyService;

        @PostMapping(value = CONTACT_DETAILS_URL,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Object> addContactDetails(@RequestBody ContactDetails contactDetails) {
                return proxyService.addContactDetails(contactDetails);
        }

        
        @PutMapping(value = CONTACT_DETAILS_URL + OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Object> changeContactDetails(@PathVariable(value = "oID") Long oID,
                                                           @RequestBody ContactDetails contactDetails) {
               return proxyService.changeContactDetails(oID, contactDetails);
        }


        @GetMapping(value = CONTACT_DETAILS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ContactDetails findContactDetails(@PathVariable(value = "oID") Long oID) {
                return proxyService.findContactDetails(oID);
        }


        @GetMapping(value = CONTACT_DETAILS_URL + "/all", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public Set<ContactDetails> findAllContactDetails() {
                return proxyService.findAllContactDetails();
        }

        @GetMapping(value = CONTACT_DETAILS_URL + CONTACT_TYPES_URL, consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public Set<ContactType> findAllContactTypes() {
                return proxyService.findAllContactTypes();
        }

        @DeleteMapping(value = CONTACT_DETAILS_URL + OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public void deleteContactDetails(@PathVariable(value = "oID") Long oID) {
                proxyService.deleteContactDetails(oID);
        }

        /* Contact Number API */
    
        @PostMapping(value = CONTACT_NUMBER_URL + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object> addContactDetailsContactNumber(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                @RequestBody ContactNumber contactNumber) {
                return proxyService.addContactDetailsContactNumber(contactDetailsOID, contactNumber);
        }

        @PutMapping(value = CONTACT_NUMBER_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object>  changeContactDetailsContactNumber(@PathVariable(value = "oID") Long oID,
                                                                  @RequestBody ContactNumber contactNumber) {
                return proxyService.changeContactDetailsContactNumber(oID, contactNumber);
        }

        @GetMapping(value = CONTACT_NUMBER_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ContactNumber findContactDetailsContactNumber(@PathVariable(value = "oID") Long oID) {
                return proxyService.findContactDetailsContactNumber(oID);
        }

        @GetMapping(value = CONTACT_NUMBER_URL + "/all" + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        Set<ContactNumber> findAllContactDetailsContactNumbers(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID){
                return proxyService.findAllContactDetailsContactNumbers(contactDetailsOID);
        }

        @DeleteMapping(value = CONTACT_NUMBER_URL + CONTACT_DETAILS_OID_PATH_VARIABLE + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
        void deleteContactDetailsContactNumber(@PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                                               @PathVariable(value = "oID") Long oID){
                proxyService.deleteContactDetailsContactNumber(contactDetailsOID, oID);
        }  
        
        /* Contact Address API */
    
        @PostMapping(value = CONTACT_ADDRESS_URL + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object> addContactDetailsContactAddress(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                @RequestBody ContactAddress contactAddress) {
                return proxyService.addContactDetailsContactAddress(contactDetailsOID, contactAddress);
        }

        @PutMapping(value = CONTACT_ADDRESS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object>  changeContactDetailsContactAddress(@PathVariable(value = "oID") Long oID,
                                                                  @RequestBody ContactAddress contactAddress) {
                return proxyService.changeContactDetailsContactAddress(oID, contactAddress);
        }

        @GetMapping(value = CONTACT_ADDRESS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ContactAddress findContactDetailsContactAddress(@PathVariable(value = "oID") Long oID) {
                return proxyService.findContactDetailsContactAddress(oID);
        }

        @GetMapping(value = CONTACT_ADDRESS_URL + "/all" + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        Set<ContactAddress> findAllContactDetailsContactAddresses(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID){
                return proxyService.findAllContactDetailsContactAddresses(contactDetailsOID);
        }

        @DeleteMapping(value = CONTACT_ADDRESS_URL + CONTACT_DETAILS_OID_PATH_VARIABLE + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
        void deleteContactDetailsContactAddress(@PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                                               @PathVariable(value = "oID") Long oID){
                proxyService.deleteContactDetailsContactAddress(contactDetailsOID, oID);
        }
        
        /* Contact Email Address API */
    
        @PostMapping(value = CONTACT_EMAIL_ADDRESS_URL + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object> addContactDetailsContactEmailAddress(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                @RequestBody ContactEmailAddress contactEmailAddress) {
                return proxyService.addContactDetailsContactEmailAddress(contactDetailsOID, contactEmailAddress);
        }

        @PutMapping(value = CONTACT_EMAIL_ADDRESS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object>  changeContactDetailsContactEmailAddress(@PathVariable(value = "oID") Long oID,
                                                                  @RequestBody ContactEmailAddress contactEmailAddress) {
                return proxyService.changeContactDetailsContactEmailAddress(oID, contactEmailAddress);
        }

        @GetMapping(value = CONTACT_EMAIL_ADDRESS_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ContactEmailAddress findContactDetailsContactEmailAddress(@PathVariable(value = "oID") Long oID) {
                return proxyService.findContactDetailsContactEmailAddress(oID);
        }

        @GetMapping(value = CONTACT_EMAIL_ADDRESS_URL + "/all" + CONTACT_DETAILS_OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        Set<ContactEmailAddress> findAllContactDetailsContactEmailAddresses(
                @PathVariable(value = "contactDetailsOID") Long contactDetailsOID){
                return proxyService.findAllContactDetailsContactEmailAddresses(contactDetailsOID);
        }

        @DeleteMapping(value = CONTACT_EMAIL_ADDRESS_URL + CONTACT_DETAILS_OID_PATH_VARIABLE + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
        void deleteContactDetailsContactEmailAddress(@PathVariable(value = "contactDetailsOID") Long contactDetailsOID,
                                               @PathVariable(value = "oID") Long oID){
                proxyService.deleteContactDetailsContactEmailAddress(contactDetailsOID, oID);
        }
}