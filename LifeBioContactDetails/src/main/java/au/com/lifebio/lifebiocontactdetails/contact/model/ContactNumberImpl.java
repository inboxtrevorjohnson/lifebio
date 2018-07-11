package au.com.lifebio.lifebiocontactdetails.contact.model;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Trevor on 2018/06/20.
 */
@Entity
public class ContactNumberImpl extends ContactMeansImpl implements ContactNumber{

    private static final String PHONE_PATTERN = "(\\d)+";

    @Size(min=10, max=12, message = "Invalid contact number, number must be between 10 and 12 characters long.")
    @Pattern(regexp = PHONE_PATTERN, message = "A number including only digits must be specified.")
    private String number;

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String getNumber() {
        return number;
    }
}
