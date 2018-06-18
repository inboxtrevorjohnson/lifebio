package au.com.lifebio.lifebioperson.person.doctor;

import au.com.lifebio.lifebioperson.person.Person;
import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;

/**
 * Created by Trevor on 2018/06/18.
 */
public interface Doctor extends Person, ServiceProvider {

    void setReferringDoctor(boolean referringDoctor);

    boolean isReferringDoctor();

    void setSolePractitioner(boolean solePractitioner);

    boolean isSolePractitioner();

}
