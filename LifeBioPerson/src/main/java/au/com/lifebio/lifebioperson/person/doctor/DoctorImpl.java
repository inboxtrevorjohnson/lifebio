package au.com.lifebio.lifebioperson.person.doctor;


import au.com.lifebio.lifebioperson.person.PersonImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Trevor on 2018/06/18.
 */

@Entity
public class DoctorImpl extends PersonImpl implements Doctor   {

    @NotEmpty
    private String practiseNumber;
    private boolean isReferringDoctor, solePractitioner;
    @NotEmpty
    @Column(unique = true)
    private String name;

    @Override
    public void setPractiseNumber(String practiseNumber) {
        this.practiseNumber = practiseNumber;
    }

    @Override
    public String getPractiseNumber() {
        return practiseNumber;
    }

    @Override
    public void setReferringDoctor(boolean referringDoctor) {
        this.isReferringDoctor = isReferringDoctor;
    }

    @Override
    public boolean isReferringDoctor() {
        return isReferringDoctor;
    }

    @Override
    public void setServiceProviderName(String name) {
        this.name = name;
    }

    @Override
    public void setSolePractitioner(boolean solePractitioner) {
        this.solePractitioner = solePractitioner;
    }

    @Override
    public boolean isSolePractitioner() {
        return this.solePractitioner;
    }

    @Override
    public String getServiceProviderName() {
        return this.name;
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }
}
