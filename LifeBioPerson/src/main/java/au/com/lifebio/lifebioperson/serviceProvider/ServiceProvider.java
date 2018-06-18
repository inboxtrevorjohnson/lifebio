package au.com.lifebio.lifebioperson.serviceProvider;

import au.com.lifebio.lifebioperson.common.CommonParent;

/**
 * Created by Trevor on 2018/06/18.
 */

public interface ServiceProvider  extends CommonParent {

    void setPractiseNumber(String practiseNumber);

    String getPractiseNumber();

    void setServiceProviderName(String serviceProviderName);

    String getServiceProviderName();

}
