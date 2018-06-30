package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.common.CommonParentImpl;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Trevor on 2018/06/20.
 */
@Entity
public class ContactDetailsImpl extends CommonParentImpl implements ContactDetails{

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<ContactAddressImpl> contactAddresses;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<ContactNumberImpl> contactNumbers;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<ContactEmailAddressImpl> contactEmailAddresses;

    @Override
    public void setContactAddresses(List<ContactAddressImpl> contactAddresses) {
        this.contactAddresses = contactAddresses;
    }

    @Override
    public List<ContactAddressImpl> getContactAddresses() {
        return contactAddresses;
    }

    @Override
    public void setContactNumbers(List<ContactNumberImpl> contactNumbers) {
        this.contactNumbers = contactNumbers;
    }

    @Override
    public List<ContactNumberImpl> getContactNumbers() {
        return contactNumbers;
    }

    @Override
    public void setContactEmailAddresses(List<ContactEmailAddressImpl> contactEmailAddresses) {
        this.contactEmailAddresses = contactEmailAddresses;
    }

    @Override
    public List<ContactEmailAddressImpl> getContactEmailAddresses() {
        return contactEmailAddresses;
    }
}
