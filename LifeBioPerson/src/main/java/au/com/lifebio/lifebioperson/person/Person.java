package au.com.lifebio.lifebioperson.person;

import au.com.lifebio.lifebioperson.common.CommonParent;

/**
 * The <code>Person</code> is used as a 'container' to
 * represent a persons state.
 *
 * @author <a href="mailto:inboxtrevorjohnson@gmail.com">ALR</a>
 */

public interface Person extends CommonParent {

    void setFirstName(String firstName);

    String getFirstName();

    void setMiddleName(String middleName);

    String getMiddleName();

    void setSurname(String surname);

    String getNickName();

    void setTitle(Title title);

    Gender getTitle();

    void setGender(Gender gender);

    Gender getGender();

    void setIdentificationNumber(String identificationNumber);

    String getIdentificationNumber();

    void setDateOfBirth(Long dateOfBirth);

    String getDateOfBirth();

    public enum Gender {
        MALE("Male"),
        FEMALE("Female");

        Gender(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

        @Override
        public String toString() {
            if (this.description == null || this.description.length() <= 0) {
                return null;
            }
            return this.description;
        }

        private String description;
    }

    public enum Title {
        MR("Mr"),
        MS("Ms"),
        MISS("Miss"),
        MRS("Miss"),
        ADV("Miss"),
        CAPT("Miss"),
        DR("Miss"),
        PROF("Miss"),
        SIR("Miss"),
        LADY("Miss");

        Title(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

        @Override
        public String toString() {
            if (this.description == null || this.description.length() <= 0) {
                return null;
            }
            return this.description;
        }

        private String description;
    }

}
