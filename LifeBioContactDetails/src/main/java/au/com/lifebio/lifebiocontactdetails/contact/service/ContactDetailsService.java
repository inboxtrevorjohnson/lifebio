package au.com.lifebio.lifebiocontactdetails.contact.service;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumber;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/26.
 */
public interface ContactDetailsService {

    Optional<ContactDetails> addContactDetails(ContactDetails contactDetails);

    Optional<ContactDetails> changeContactDetails(ContactDetails contactDetails);

    Optional<ContactDetails> findContactDetails(Long oID);

    Optional<Set<ContactDetails>> findAllContactDetailsByOID(Set<Long> contactDetailsOIDs);

    Optional<Set<ContactDetails>> findAll();

    void deleteContactDetails(ContactDetails contactDetails);

}
