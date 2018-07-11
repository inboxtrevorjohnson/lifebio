package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactNumberRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumber;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumberImpl;
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
public class ContactNumberRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactNumberRepository contactNumberRepository;

    @Test
    public void whenFindByOID_thenReturnContactNumber() {
        /* Given */
        String number = RandomStringUtils.randomNumeric(12);
        ContactNumber given = new ContactNumberImpl();
        given.setNumber(number);
        given.setContactType(ContactType.BUSINESS);
        given.setLastModified(LocalDateTime.now());

        Long oID = entityManager.persist(given).getOID();
        entityManager.flush();

        /* When */
        Optional<ContactNumberImpl> found = contactNumberRepository.findById(oID);

        /* Then */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

}
