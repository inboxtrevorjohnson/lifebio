package au.com.lifebio.lifebioperson.person;

import au.com.lifebio.lifebiocommon.common.CommonParent;
import au.com.lifebio.lifebioperson.contact.Contactable;

import java.time.LocalDateTime;

/**
 * Created by Trevor on 2018/06/18.
 */

public interface Person extends CommonParent, Contactable {

    void setFirstName(String firstName);

    String getFirstName();

    void setMiddleName(String middleName);

    String getMiddleName();

    void setSurname(String surname);

    String getSurname();

    void setNickName(String nickName);

    String getNickName();

    void setTitle(Title title);

    Title getTitle();

    void setGender(Gender gender);

    Gender getGender();

    void setIdentificationNumber(String identificationNumber);

    String getIdentificationNumber();

    void setDateOfBirth(LocalDateTime dateOfBirth);

    LocalDateTime getDateOfBirth();

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
