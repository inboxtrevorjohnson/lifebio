package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactAddressRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddress;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactAddressImpl;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactType;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Trevor on 2018/06/20.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ContactAddressRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactAddressRepository contactAddressRepository;

    @Test
    public void whenFindByOID_thenReturnContactAddress() {
        /* Given */
        String line1 = RandomStringUtils.randomAlphanumeric(10);
        String line2 = RandomStringUtils.randomAlphanumeric(10);
        String line3 = RandomStringUtils.randomAlphanumeric(10);
        String cityArea = RandomStringUtils.randomAlphanumeric(10);
        String state = RandomStringUtils.randomAlphanumeric(10);
        String postalCode = RandomStringUtils.randomAlphanumeric(10);
        String country = RandomStringUtils.randomAlphanumeric(10);

        ContactAddress given = new ContactAddressImpl();
        given.setLine1(line1);
        given.setLine2(line2);
        given.setLine3(line3);
        given.setCityArea(cityArea);
        given.setState(state);
        given.setPostalCode(postalCode);
        given.setCountry(country);
        given.setContactType(ContactType.BUSINESS);
        given.setLastModified(LocalDateTime.now());

        Long oID = entityManager.persist(given).getOID();
        entityManager.flush();

        /* When */
        Optional<ContactAddressImpl> found = contactAddressRepository.findById(oID);

        /* Then */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

}
