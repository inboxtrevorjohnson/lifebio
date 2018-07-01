package au.com.lifebio.lifebioperson.serviceProvider;

import au.com.lifebio.lifebiocommon.common.CommonParent;
import au.com.lifebio.lifebioperson.contact.Contactable;

/**
 * Created by Trevor on 2018/06/18.
 */

public interface ServiceProvider  extends CommonParent, Contactable {

    void setPractiseNumber(String practiseNumber);

    String getPractiseNumber();

    void setServiceProviderName(String serviceProviderName);

    String getServiceProviderName();

}
