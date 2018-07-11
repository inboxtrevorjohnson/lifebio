package au.com.lifebio.lifebiocontactdetails.contact.controller;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddressImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Trevor on 2018/07/11.
 */
public interface ContactMeansController {

    String CONTACT_DETAILS_OID_PATH_VARIABLE = "/{contactDetailsOID}";
    String OID_PATH_VARIABLE = "/{oID}";

}
