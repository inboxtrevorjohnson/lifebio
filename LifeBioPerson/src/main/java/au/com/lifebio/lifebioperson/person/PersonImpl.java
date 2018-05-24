package au.com.lifebio.lifebioperson.person;


import au.com.lifebio.lifebioperson.common.CommonParentImpl;

/**
 * The <code>PersonImpl</code> is used as a 'container' to
 * represent a persons state.
 *
 * @author <a href="mailto:inboxtrevorjohnson@gmail.com">ALR</a>
 */

public class PersonImpl extends CommonParentImpl implements Person   {
    private Long dateOfBirth;
    private String firstName, middleName, surname, nickName, identificationNumber;
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
    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public Long getDateOfBirth() {
        return dateOfBirth;
    }

}
