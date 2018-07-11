package au.com.lifebio.lifebiocontactdetails.contact.controller;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumber;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumberImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Trevor on 2018/07/10.
 */
public interface ContactDetailsContactNumberController extends ContactMeansController{

    String CONTACT_NUMBER_URL = "/contactdetails/contactnumber";

    ResponseEntity<Object> addContactDetailsContactNumber(Long contactDetailsOID, ContactNumber contactNumber);

    ResponseEntity<Object>  changeContactDetailsContactNumber(Long oID, ContactNumber contactNumber);

    ContactNumber findContactDetailsContactNumber(Long oID);

    List<ContactNumberImpl> findAllContactDetailsContactNumbers(Long contactDetailsOID);

    void deleteContactDetailsContactNumber(Long contactDetailsOID, Long oID);

}
