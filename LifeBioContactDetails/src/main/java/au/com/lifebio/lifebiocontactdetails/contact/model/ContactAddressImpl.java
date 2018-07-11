package au.com.lifebio.lifebiocontactdetails.contact.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Trevor on 2018/06/20.
 */
@Entity
public class ContactAddressImpl extends ContactMeansImpl implements ContactAddress{

    @NotEmpty
    private String line1, state, cityArea, postalCode, country;
    private String line2, line3;

    @Override
    public void setLine1(String line1) {
        this.line1 = line1;
    }

    @Override
    public String getLine1() {
        return line1;
    }

    @Override
    public void setLine2(String line2) {
        this.line2 = line2;
    }

    @Override
    public String getLine2() {
        return this.line2;
    }

    @Override
    public void setLine3(String line3) {
        this.line3 = line3;
    }

    @Override
    public String getLine3() {
        return this.line3;
    }

    @Override
    public void setCityArea(String cityArea) {
        this.cityArea = cityArea;
    }

    @Override
    public String getCityArea() {
        return cityArea;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getCountry() {
        return country;
    }
}
