package au.com.lifebio.lifebioperson.serviceProvider;

import au.com.lifebio.lifebioperson.common.CommonParentImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Trevor on 2018/06/18.
 */

@Entity
public class ServiceProviderImpl extends CommonParentImpl implements ServiceProvider  {

    @NotEmpty
    private String practiseNumber;
    @NotEmpty
    @Column(unique = true)
    private String serviceProviderName;

    @Override
    public void setPractiseNumber(String practiseNumber) {
        this.practiseNumber = practiseNumber;
    }

    @Override
    public String getPractiseNumber() {
        return practiseNumber;
    }

    @Override
    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    @Override
    public String getServiceProviderName() {
        return this.serviceProviderName;
    }
}
