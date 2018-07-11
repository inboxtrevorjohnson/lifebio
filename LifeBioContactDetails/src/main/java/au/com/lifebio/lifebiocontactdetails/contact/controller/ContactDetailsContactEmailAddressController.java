package au.com.lifebio.lifebiocontactdetails.contact.controller;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddressImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Trevor on 2018/07/11.
 */
public interface ContactDetailsContactEmailAddressController extends ContactMeansController{

    String CONTACT_EMAIL_ADDRESS_URL = "/contactdetails/contactemailaddress";

    ResponseEntity<Object> addContactDetailsContactEmailAddress(Long contactDetailsOID, ContactEmailAddress
            contactEmailAddress);

    ResponseEntity<Object>  changeContactDetailsContactEmailAddress(Long oID, ContactEmailAddress contactEmailAddress);

    ContactEmailAddress findContactDetailsContactEmailAddress(Long oID);

    List<ContactEmailAddressImpl> findAllContactDetailsContactEmailAddresses(Long contactDetailsOID);

    void deleteContactDetailsContactEmailAddress(Long contactDetailsOID, Long oID);

}
