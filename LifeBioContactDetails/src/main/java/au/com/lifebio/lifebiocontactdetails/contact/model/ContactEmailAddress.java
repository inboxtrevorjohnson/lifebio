package au.com.lifebio.lifebiocontactdetails.contact.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Trevor on 2018/06/20.
 */
@JsonDeserialize(as = ContactEmailAddressImpl.class)
public interface ContactEmailAddress extends ContactMeans {

    void setEmailAddress(String emailAddress);

    String getEmailAddress();
}
