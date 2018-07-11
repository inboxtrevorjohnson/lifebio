package au.com.lifebio.lifebiocontactdetails.contact.service;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumber;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumberImpl;

import java.util.List;
import java.util.Optional;

/**
 * Created by Trevor on 2018/07/09.
 */
public interface ContactNumberService {

    Optional<ContactNumber> addContactDetailsContactNumber(Long contactDetailsOID, ContactNumber contactNumber);

    Optional<ContactNumber> changeContactDetailsContactNumber(ContactNumber contactNumber);

    Optional<ContactNumber> findContactDetailsContactNumber(Long oID);

    Optional<List<ContactNumberImpl>> findAllContactDetailsContactNumbers(Long contactDetailsOID);

    void deleteContactDetailsContactNumber(Long contactDetailsOID, ContactNumber contactNumber);

}
