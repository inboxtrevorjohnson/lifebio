package au.com.lifebio.lifebiocontactdetails.contact.model;

import au.com.lifebio.lifebiocommon.common.CommonParent;

/**
 * Created by Trevor on 2018/06/20.
 */
public interface ContactMeans extends CommonParent {

    void setContactType(ContactType contactType);

    ContactType getContactType();
}
