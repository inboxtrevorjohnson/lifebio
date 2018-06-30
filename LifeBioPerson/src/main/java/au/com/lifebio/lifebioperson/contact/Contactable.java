package au.com.lifebio.lifebioperson.contact;

import au.com.lifebio.lifebioperson.common.CommonParent;

/**
 * Created by Trevor on 2018/06/26.
 */

public interface Contactable extends CommonParent {

    void setContactDetails(Long contactDetailsOID);

    Long getContactDetails();

}
