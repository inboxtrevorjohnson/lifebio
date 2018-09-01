package au.com.lifebio.lifebiocontactdetails.contact.service;

import au.com.lifebio.lifebiocommon.common.exception.*;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactEmailAddressRepository;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactEmailAddressImpl;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/07/10.
 */
@Service
@Transactional
public class ContactEmailAddressServiceImpl implements ContactEmailAddressService {

    @Autowired
    private ContactDetailsService contactDetailsService;

    @Autowired
    private ContactEmailAddressRepository contactEmailAddressRepository;

    @Override
    @PreAuthorize("hasAuthority('CONTACT_DETAILS_CREATE')")
    public Optional<ContactEmailAddress> addContactDetailsContactEmailAddress(
            @NotNull(message = "Cannot add contact email address with null contact details.") Long contactDetailsOID,
            @NotNull(message = "Cannot add null contact email address.") ContactEmailAddress contactEmailAddress) {

        contactEmailAddress.setLastModified(LocalDateTime.now());

        contactEmailAddress = contactEmailAddressRepository.save((ContactEmailAddressImpl) contactEmailAddress);

        ContactDetails contactDetails = contactDetailsService.findContactDetails(contactDetailsOID).orElseThrow(
                () -> new CreationException("Cannot add contact email address, cannot find contact details."));
        
        List<ContactEmailAddressImpl> contactEmailAddresses = new ArrayList<>(contactDetails.getContactEmailAddresses());
        contactEmailAddresses.add((ContactEmailAddressImpl) contactEmailAddress);
        contactDetails.setContactEmailAddresses(contactEmailAddresses);

        contactDetailsService.changeContactDetails(contactDetails);

        return Optional.of(contactEmailAddress);

    }

    @Override
    @PreAuthorize("hasAuthority('CONTACT_DETAILS_UPDATE')")
    public Optional<ContactEmailAddress> changeContactDetailsContactEmailAddress(
            @NotNull(message = "Cannot change null contact email address.") ContactEmailAddress contactEmailAddress) {

        if(contactEmailAddress.getOID() == null){
            throw new ModificationException("Cannot change contact email address, the oid is null!");
        }

        if(!contactEmailAddress.getLastModified().equals(contactEmailAddressRepository.findById(contactEmailAddress
                .getOID()).orElseThrow(() -> new ResourceNotFoundException("Cannot change contact email address, " +
                "cannot find contact address!")).getLastModified())){
            throw new ConflictException("Cannot change contact email address, it has been modified by someone else!");
        }

        if(contactEmailAddress instanceof ContactEmailAddressImpl) {
            contactEmailAddress.setLastModified(LocalDateTime.now());
            return Optional.of(contactEmailAddressRepository.save((ContactEmailAddressImpl) contactEmailAddress));
        }

        throw new TypeNotSupportedException("Contact email address is not a supported type!");
    }

    @Override
    @PreAuthorize("hasAuthority('CONTACT_DETAILS_READ')")
    public Optional<ContactEmailAddress> findContactDetailsContactEmailAddress(
            @NotNull(message = "Cannot find contact details with a null OID.") Long oID) {
        Optional<ContactEmailAddress> optional = Optional.of(contactEmailAddressRepository.findById(oID).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find contact email address by OID")
        ));
        return optional;
    }

    @Override
    @PreAuthorize("hasAuthority('CONTACT_DETAILS_READ')")
    public Optional<List<ContactEmailAddressImpl>> findAllContactDetailsContactEmailAddresses(
            @NotNull(message = "Cannot find contact email addresss with a null contact details OID.")
                    Long contactDetailsOID) {
        Optional<List<ContactEmailAddressImpl>> optional = Optional.of(contactDetailsService.findContactDetails(
                contactDetailsOID).orElseThrow(() -> new ResourceNotFoundException(
                        "Unable to find contact email addresss by contact details OID")).getContactEmailAddresses());
        return optional;
    }

    @Override
    @PreAuthorize("hasAuthority('CONTACT_DETAILS_DELETE')")
    public void deleteContactDetailsContactEmailAddress(
            @NotNull(message = "Cannot delete contact email address, a valid contact details oid must be specified.")
                    Long contactDetailsOID,
            @NotNull(message = "Cannot delete contact email address, a valid contact email address must be specified.")
                    ContactEmailAddress contactEmailAddress) {
        if(contactEmailAddress.getOID() == null){
            throw new RemoveException("Cannot delete contact email address, contact email address OID is null!");
        }
        //contactEmailAddressRepository.delete((ContactEmailAddressImpl) contactEmailAddress);

        ContactDetails contactDetails = contactDetailsService.findContactDetails(contactDetailsOID).orElseThrow(
                () -> new RemoveException("Cannot delete contact email address, cannot find contact details"));

        List<ContactEmailAddressImpl> contactEmailAddresses = new ArrayList<>(contactDetails.getContactEmailAddresses());
        contactEmailAddresses.remove(contactEmailAddress);

        contactDetails.setContactEmailAddresses(contactEmailAddresses);
        contactDetailsService.changeContactDetails(contactDetails);

    }
}
