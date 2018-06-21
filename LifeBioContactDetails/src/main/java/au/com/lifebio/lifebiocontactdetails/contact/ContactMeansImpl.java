package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.common.CommonParentImpl;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Trevor on 2018/06/20.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
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
