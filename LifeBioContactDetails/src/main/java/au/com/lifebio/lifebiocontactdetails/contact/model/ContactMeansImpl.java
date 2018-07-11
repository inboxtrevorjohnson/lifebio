package au.com.lifebio.lifebiocontactdetails.contact.model;

import au.com.lifebio.lifebiocommon.common.CommonParentImpl;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Created by Trevor on 2018/06/20.
 */
@MappedSuperclass
public class ContactMeansImpl extends CommonParentImpl implements ContactMeans{
    @NotNull(message = "A contact type must be specified.")
    private ContactType contactType;

    @Override
    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    @Override
    public ContactType getContactType() {
        return contactType;
    }
}
