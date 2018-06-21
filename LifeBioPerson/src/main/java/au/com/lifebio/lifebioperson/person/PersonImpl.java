package au.com.lifebio.lifebioperson.person;


import au.com.lifebio.lifebioperson.common.CommonParentImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

/**
 * Created by Trevor on 2018/06/18.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class PersonImpl extends CommonParentImpl implements Person   {
    private @Past LocalDateTime dateOfBirth;
    @NotEmpty(message = "First name and surname cannot be empty.")
    private String firstName, surname;
    private String nickName, middleName;
    @NotEmpty(message = "Identification number cannot be empty.")
    @Column(unique = true)
    private String identificationNumber;
    @NotNull(message = "A title must be specified.")
    private Person.Title title;
    private Person.Gender gender;

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public void setTitle(Person.Title title) {
        this.title = title;
    }

    @Override
    public Person.Title getTitle() {
        return title;
    }

    @Override
    public void setGender(Person.Gender gender) {
        this.gender = gender;
    }

    @Override
    public Person.Gender getGender() {
        return gender;
    }

    @Override
    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    @Override
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    @Override
    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

}
