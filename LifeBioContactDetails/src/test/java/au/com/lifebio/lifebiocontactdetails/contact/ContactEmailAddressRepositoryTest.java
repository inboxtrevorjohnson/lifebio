package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactEmailAddressRepository;
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
public class ContactEmailAddressRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactEmailAddressRepository contactEmailAddressRepository;

    @Test
    public void whenFindByOID_thenReturnContactEmailAddress() {
        /* Given */
        String emailAddress = RandomStringUtils.randomAlphabetic(4) + "@" + RandomStringUtils.randomAlphabetic(6)
                + "." + RandomStringUtils.randomAlphabetic(3);
        ContactEmailAddress given = new ContactEmailAddressImpl();
        given.setEmailAddress(emailAddress);
        given.setContactType(ContactType.BUSINESS);
        given.setLastModified(LocalDateTime.now());

        Long oID = entityManager.persist(given).getOID();
        entityManager.flush();

        /* When */
        Optional<ContactEmailAddressImpl> found = contactEmailAddressRepository.findById(oID);

        /* Then */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

}
