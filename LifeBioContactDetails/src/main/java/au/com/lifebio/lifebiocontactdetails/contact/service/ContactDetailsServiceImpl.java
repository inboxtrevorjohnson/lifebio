package au.com.lifebio.lifebiocontactdetails.contact.service;

import au.com.lifebio.lifebiocontactdetails.contact.ContactDetails;
import au.com.lifebio.lifebiocontactdetails.contact.ContactDetailsImpl;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocommon.common.exception.TypeNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/26.
 */
public class ContactDetailsServiceImpl implements ContactDetailsService {

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Override
    public Optional<ContactDetails> addContactDetails(@NotNull(message = "Cannot add null contact details.")
                                                                  ContactDetails contactDetails) {
        contactDetails.setLastModified(LocalDateTime.now());
        return Optional.of(contactDetailsRepository.save((ContactDetailsImpl)contactDetails));
    }

    @Override
    public Optional<ContactDetails> changeContactDetails(@NotNull(message = "Cannot change null contactDetails.")
                                                                     ContactDetails contactDetails) {
        if(contactDetails instanceof ContactDetailsImpl) {
            contactDetails.setLastModified(LocalDateTime.now());
            return Optional.of(contactDetailsRepository.save((ContactDetailsImpl)contactDetails));
        }
        throw new TypeNotSupportedException("Contact details is not a supported type!");
    }

    @Override
    public Optional<ContactDetails> findContactDetails(@NotNull(message = "Cannot find contact details with a null " +
            "OID.") Long oID) {
        Optional<ContactDetails> optional = Optional.of(contactDetailsRepository.findById(oID).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find contact details by OID")
        ));
        return optional;
    }

    @Override
    public Optional<Set<ContactDetails>> findAll() {
        return Optional.of(new HashSet<ContactDetails>(contactDetailsRepository.findAll()));
    }

    @Override
    public Optional<Set<ContactDetails>> findAllContactDetailsByOID(@NotNull(message = "Cannot find all contact " +
            "details by oid with out an valid list.") Set<Long> contactDetailsOIDs) {
        return Optional.of(new HashSet<ContactDetails>(contactDetailsRepository.findAllById(contactDetailsOIDs)));
    }

    @Override
    public void deleteContactDetails(@NotNull(message = "Cannot delete contactDetails, a valid contact details must be "
            + "specified.") ContactDetails contactDetails) {
        if(contactDetails instanceof ContactDetailsImpl) {
            contactDetailsRepository.delete((ContactDetailsImpl) contactDetails);
        }
        else {
            throw new TypeNotSupportedException("ContactDetails is not a supported type!");
        }

    }    
}
