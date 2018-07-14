package au.com.lifebio.lifebiocontactdetails.contact.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Trevor on 2018/06/20.
 */
public enum ContactType {

    PERSONAL("Personal"),
    BUSINESS("Business"),
    RESIDENTIAL("Residential");


    ContactType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        if (this.description == null || this.description.length() <= 0) {
            return null;
        }
        return this.description;
    }

    private String description;

}
