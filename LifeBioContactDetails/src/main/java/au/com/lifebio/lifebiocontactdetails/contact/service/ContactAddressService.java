package au.com.lifebio.lifebiocontactdetails.contact.service;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddressImpl;

import java.util.List;
import java.util.Optional;

/**
 * Created by Trevor on 2018/07/10.
 */
public interface ContactAddressService {

    Optional<ContactAddress> addContactDetailsContactAddress(Long contactDetailsOID, ContactAddress contactAddress);

    Optional<ContactAddress> changeContactDetailsContactAddress(ContactAddress contactAddress);

    Optional<ContactAddress> findContactDetailsContactAddress(Long oID);

    Optional<List<ContactAddressImpl>> findAllContactDetailsContactAddresses(Long contactDetailsOID);

    void deleteContactDetailsContactAddress( Long contactDetailsOID, ContactAddress contactAddress);

}
