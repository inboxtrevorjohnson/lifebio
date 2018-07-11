package au.com.lifebio.lifebiocontactdetails.contact.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Trevor on 2018/06/20.
 */
@JsonDeserialize(as = ContactAddressImpl.class)
public interface ContactAddress extends ContactMeans {

    void setLine1(String line1);

    String getLine1();

    void setLine2(String line2);

    String getLine2();

    void setLine3(String line3);

    String getLine3();

    void setCityArea(String cityArea);

    String getCityArea();

    void setState(String state);

    String getState();

    void setPostalCode(String postalCode);

    String getPostalCode();

    void setCountry(String country);

    String getCountry();
}
