package au.com.lifebio.lifebiocontactdetails.contact.service;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebiocommon.common.exception.ModificationException;
import au.com.lifebio.lifebiocommon.common.exception.RemoveException;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactNumberRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetailsImpl;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocommon.common.exception.TypeNotSupportedException;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumber;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumberImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/26.
 */
@Service
@Transactional
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
    public Optional<ContactDetails> changeContactDetails(@NotNull(message = "Cannot change null contact details.")
                                                                     ContactDetails contactDetails) {
        if(contactDetails.getOID() == null){
            throw new ModificationException("Cannot change contact details, the oid is null!");
        }
        if(!contactDetails.getLastModified().equals(contactDetailsRepository.findById(contactDetails.getOID())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot change contact details, cannot find contact " +
                        "details!"))
                .getLastModified())){
            throw new ModificationException("Cannot change contact details, it has been modified by someone else!");
        }
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
    public void deleteContactDetails(@NotNull(message = "Cannot delete contact details, a valid contact details must " +
            "be specified.") ContactDetails contactDetails) {
        if(contactDetails.getOID() == null){
            throw new RemoveException("Cannot delete null contact details!");
        }
        contactDetailsRepository.delete((ContactDetailsImpl) contactDetails);

    }

}
