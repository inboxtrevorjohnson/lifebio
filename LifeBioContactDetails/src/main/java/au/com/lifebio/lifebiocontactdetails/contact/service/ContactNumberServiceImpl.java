package au.com.lifebio.lifebiocontactdetails.contact.service;

import au.com.lifebio.lifebiocommon.common.exception.*;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactNumberRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetailsImpl;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumber;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumberImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Trevor on 2018/07/09.
 */
@Service
@Transactional
public class ContactNumberServiceImpl implements ContactNumberService {

    @Autowired
    private ContactDetailsService contactDetailsService;

    @Autowired
    private ContactNumberRepository contactNumberRepository;

    @Override
    @PreAuthorize("hasAuthority('CONTACT_DETAILS_CREATE')")
    public Optional<ContactNumber> addContactDetailsContactNumber(
            @NotNull(message = "Cannot add contact number with null contact details.") Long contactDetailsOID,
            @NotNull(message = "Cannot add null contact number.") ContactNumber contactNumber) {

        contactNumber.setLastModified(LocalDateTime.now());

        contactNumber = contactNumberRepository.save((ContactNumberImpl) contactNumber);

        ContactDetails contactDetails = contactDetailsService.findContactDetails(contactDetailsOID).orElseThrow(
                () -> new CreationException("Cannot add contact number, cannot find contact details."));

        List<ContactNumberImpl> contactNumbers = new ArrayList<>(contactDetails.getContactNumbers());
        contactNumbers.add((ContactNumberImpl) contactNumber);
        contactDetails.setContactNumbers(contactNumbers);

        contactDetailsService.changeContactDetails(contactDetails);

        return Optional.of(contactNumber);

    }

    @Override
    @PreAuthorize("hasAuthority('CONTACT_DETAILS_UPDATE')")
    public Optional<ContactNumber> changeContactDetailsContactNumber(
            @NotNull(message = "Cannot change null contact number.") ContactNumber contactNumber) {

        if(contactNumber.getOID() == null){
            throw new ModificationException("Cannot change contact number, the oid is null!");
        }

        if(!contactNumber.getLastModified().equals(contactNumberRepository.findById(contactNumber.getOID())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot change contact number, cannot find contact " +
                        "number!")).getLastModified())){
            throw new ConflictException("Cannot change contact number, it has been modified by someone else!");
        }

        if(contactNumber instanceof ContactNumberImpl) {
            contactNumber.setLastModified(LocalDateTime.now());
            return Optional.of(contactNumberRepository.save((ContactNumberImpl) contactNumber));
        }

        throw new TypeNotSupportedException("Contact number is not a supported type!");
    }

    @Override
    @PreAuthorize("hasAuthority('CONTACT_DETAILS_READ')")
    public Optional<ContactNumber> findContactDetailsContactNumber(
            @NotNull(message = "Cannot find contact details with a null OID.") Long oID) {
        Optional<ContactNumber> optional = Optional.of(contactNumberRepository.findById(oID).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find contact number by OID")
        ));
        return optional;
    }

    @Override
    @PreAuthorize("hasAuthority('CONTACT_DETAILS_READ')")
    public Optional<List<ContactNumberImpl>> findAllContactDetailsContactNumbers(
            @NotNull(message = "Cannot find contact numbers with a null contact details OID.") Long contactDetailsOID) {
        Optional<List<ContactNumberImpl>> optional = Optional.of(contactDetailsService.findContactDetails(
                contactDetailsOID).orElseThrow(
                        () -> new ResourceNotFoundException("Unable to find contact numbers by contact details OID")
        ).getContactNumbers());
        return optional;
    }

    @Override
    @PreAuthorize("hasAuthority('CONTACT_DETAILS_READ')")
    public void deleteContactDetailsContactNumber(
            @NotNull(message = "Cannot delete contact number, a valid contact details oid must be specified.")
                    Long contactDetailsOID,
            @NotNull(message = "Cannot delete contact number, a valid contact number must be specified.")
                    ContactNumber contactNumber) {
        if(contactNumber.getOID() == null){
            throw new RemoveException("Cannot delete contact number, contact number OID is null!");
        }
        //contactNumberRepository.delete((ContactNumberImpl) contactNumber);

        ContactDetails contactDetails = contactDetailsService.findContactDetails(contactDetailsOID).orElseThrow(
                () -> new RemoveException("Cannot delete contact number, cannot find contact details"));

        List<ContactNumberImpl> contactNumbers = new ArrayList<>(contactDetails.getContactNumbers());
        contactNumbers.remove(contactNumber);

        contactDetails.setContactNumbers(contactNumbers);
        contactDetailsService.changeContactDetails(contactDetails);

    }
}
