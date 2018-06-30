package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.common.CommonParent;

import java.util.List;

/**
 * Created by Trevor on 2018/06/20.
 */

public interface ContactDetails extends CommonParent {

    void setContactAddresses(List<ContactAddressImpl> contactAddresses);

    List<ContactAddressImpl> getContactAddresses();

    void setContactNumbers(List<ContactNumberImpl> contactNumbers);

    List<ContactNumberImpl> getContactNumbers();

    void setContactEmailAddresses(List<ContactEmailAddressImpl> contactEmailAddresses);

    List<ContactEmailAddressImpl> getContactEmailAddresses();


}
