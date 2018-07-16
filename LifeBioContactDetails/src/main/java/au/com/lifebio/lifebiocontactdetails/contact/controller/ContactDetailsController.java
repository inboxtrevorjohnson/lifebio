package au.com.lifebio.lifebiocontactdetails.contact.controller;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import org.springframework.http.ResponseEntity;

import java.util.Set;

/**
 * Created by Trevor on 2018/07/05.
 */
public interface ContactDetailsController {

    String CONTACT_DETAILS_URL = "/contactdetails";
    String OID_PATH_VARIABLE = "/{oID}";
    String CONTACT_DETAILS_OID = "contactDetailsOID";
    ResponseEntity<Object> addContactDetails(ContactDetails contactDetails);

    ResponseEntity<Object>  changeContactDetails(Long oID, ContactDetails contactDetails);

    ContactDetails findContactDetails(Long oID);

    Set<ContactDetails> findAllContactDetails();

    void deleteContactDetails(Long oID);

}
