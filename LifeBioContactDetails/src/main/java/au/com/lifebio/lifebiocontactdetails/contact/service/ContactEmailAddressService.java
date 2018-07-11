package au.com.lifebio.lifebiocontactdetails.contact.service;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddressImpl;

import java.util.List;
import java.util.Optional;

/**
 * Created by Trevor on 2018/07/10.
 */
public interface ContactEmailAddressService {

    Optional<ContactEmailAddress> addContactDetailsContactEmailAddress(Long contactDetailsOID, ContactEmailAddress
            contactEmailAddress);

    Optional<ContactEmailAddress> changeContactDetailsContactEmailAddress(ContactEmailAddress contactEmailAddress);

    Optional<ContactEmailAddress> findContactDetailsContactEmailAddress(Long oID);

    Optional<List<ContactEmailAddressImpl>> findAllContactDetailsContactEmailAddresses(Long contactDetailsOID);

    void deleteContactDetailsContactEmailAddress( Long contactDetailsOID, ContactEmailAddress contactEmailAddress);

}
