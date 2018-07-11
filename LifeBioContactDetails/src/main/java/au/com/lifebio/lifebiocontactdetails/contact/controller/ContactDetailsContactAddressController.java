package au.com.lifebio.lifebiocontactdetails.contact.controller;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddressImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Trevor on 2018/07/11.
 */
public interface ContactDetailsContactAddressController extends ContactMeansController{

    String CONTACT_ADDRESS_URL = "/contactdetails/contactaddress";

    ResponseEntity<Object> addContactDetailsContactAddress(Long contactDetailsOID, ContactAddress contactAddress);

    ResponseEntity<Object>  changeContactDetailsContactAddress(Long oID, ContactAddress contactAddress);

    ContactAddress findContactDetailsContactAddress(Long oID);

    List<ContactAddressImpl> findAllContactDetailsContactAddresses(Long contactDetailsOID);

    void deleteContactDetailsContactAddress(Long contactDetailsOID, Long oID);

}
