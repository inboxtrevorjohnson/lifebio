package au.com.lifebio.lifebiocontactdetails.contact;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Created by Trevor on 2018/06/20.
 */
@Entity
public class ContactEmailAddressImpl extends ContactMeansImpl implements ContactEmailAddress{

    private static final String EMAIL_ADDRESS_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @NotEmpty(message = "Email address cannot be empty.")
    @Pattern(regexp = EMAIL_ADDRESS_PATTERN, message = "Invalid email address.")
    private String emailAddress;

    @Override
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }
}
