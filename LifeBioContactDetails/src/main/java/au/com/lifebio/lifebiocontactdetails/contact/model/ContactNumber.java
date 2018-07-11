package au.com.lifebio.lifebiocontactdetails.contact.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Trevor on 2018/06/20.
 */
@JsonDeserialize(as = ContactNumberImpl.class)
public interface ContactNumber extends ContactMeans {
    void setNumber(String number);

    String getNumber();
}
