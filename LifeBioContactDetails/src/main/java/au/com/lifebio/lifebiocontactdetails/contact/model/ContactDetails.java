package au.com.lifebio.lifebiocontactdetails.contact.model;

import au.com.lifebio.lifebiocommon.common.CommonParent;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Created by Trevor on 2018/06/20.
 */
@JsonDeserialize(as = ContactDetailsImpl.class)
public interface ContactDetails extends CommonParent {

    void setContactAddresses(List<ContactAddressImpl> contactAddresses);

    List<ContactAddressImpl> getContactAddresses();

    void setContactNumbers(List<ContactNumberImpl> contactNumbers);

    List<ContactNumberImpl> getContactNumbers();

    void setContactEmailAddresses(List<ContactEmailAddressImpl> contactEmailAddresses);

    List<ContactEmailAddressImpl> getContactEmailAddresses();


}
