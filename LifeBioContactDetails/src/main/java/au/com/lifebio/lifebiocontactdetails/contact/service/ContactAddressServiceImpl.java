package au.com.lifebio.lifebiocontactdetails.contact.service;

import au.com.lifebio.lifebiocommon.common.exception.*;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactAddressRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddressImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
public class ContactAddressServiceImpl implements ContactAddressService {

    @Autowired
    private ContactDetailsService contactDetailsService;

    @Autowired
    private ContactAddressRepository contactAddressRepository;

    @Override
    public Optional<ContactAddress> addContactDetailsContactAddress(
            @NotNull(message = "Cannot add contact address with null contact details.") Long contactDetailsOID,
            @NotNull(message = "Cannot add null contact address.") ContactAddress contactAddress) {

        contactAddress.setLastModified(LocalDateTime.now());

        contactAddress = contactAddressRepository.save((ContactAddressImpl) contactAddress);

        ContactDetails contactDetails = contactDetailsService.findContactDetails(contactDetailsOID).orElseThrow(
                () -> new CreationException("Cannot add contact address, cannot find contact details."));
        
        List<ContactAddressImpl> contactAddresses = new ArrayList<>(contactDetails.getContactAddresses());
        contactAddresses.add((ContactAddressImpl) contactAddress);
        contactDetails.setContactAddresses(contactAddresses);

        contactDetailsService.changeContactDetails(contactDetails);

        return Optional.of(contactAddress);

    }

    @Override
    public Optional<ContactAddress> changeContactDetailsContactAddress(
            @NotNull(message = "Cannot change null contact address.") ContactAddress contactAddress) {

        if(contactAddress.getOID() == null){
            throw new ModificationException("Cannot change contact address, the oid is null!");
        }

        if(!contactAddress.getLastModified().equals(contactAddressRepository.findById(contactAddress.getOID())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot change contact address, cannot find contact " +
                        "address!")).getLastModified())){
            throw new ConflictException("Cannot change contact address, it has been modified by someone else!");
        }

        if(contactAddress instanceof ContactAddressImpl) {
            contactAddress.setLastModified(LocalDateTime.now());
            return Optional.of(contactAddressRepository.save((ContactAddressImpl) contactAddress));
        }

        throw new TypeNotSupportedException("Contact address is not a supported type!");
    }

    @Override
    public Optional<ContactAddress> findContactDetailsContactAddress(
            @NotNull(message = "Cannot find contact details with a null OID.") Long oID) {
        Optional<ContactAddress> optional = Optional.of(contactAddressRepository.findById(oID).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find contact address by OID")
        ));
        return optional;
    }

    @Override
    public Optional<List<ContactAddressImpl>> findAllContactDetailsContactAddresses(
            @NotNull(message = "Cannot find contact addresss with a null contact details OID.") Long contactDetailsOID) {
        Optional<List<ContactAddressImpl>> optional = Optional.of(contactDetailsService.findContactDetails(
                contactDetailsOID).orElseThrow(
                        () -> new ResourceNotFoundException("Unable to find contact addresss by contact details OID")
        ).getContactAddresses());
        return optional;
    }

    @Override
    public void deleteContactDetailsContactAddress(
            @NotNull(message = "Cannot delete contact address, a valid contact details oid must be specified.")
                    Long contactDetailsOID,
            @NotNull(message = "Cannot delete contact address, a valid contact address must be specified.")
                    ContactAddress contactAddress) {
        if(contactAddress.getOID() == null){
            throw new RemoveException("Cannot delete contact address, contact address OID is null!");
        }
        //contactNumberRepository.delete((ContactAddressImpl) contactAddress);

        ContactDetails contactDetails = contactDetailsService.findContactDetails(contactDetailsOID).orElseThrow(
                () -> new RemoveException("Cannot delete contact address, cannot find contact details"));

        List<ContactAddressImpl> contactAddresses = new ArrayList<>(contactDetails.getContactAddresses());
        contactAddresses.remove(contactAddress);

        contactDetails.setContactAddresses(contactAddresses);
        contactDetailsService.changeContactDetails(contactDetails);

    }
}
