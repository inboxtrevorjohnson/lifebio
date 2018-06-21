package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
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
public class ContactDetailsRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Test
    public void whenFindByOID_thenReturnContactDetails() {
        /* Given */
        ContactDetails given = new ContactDetailsImpl();
        given.setLastModified(LocalDateTime.now());

        Long oID = entityManager.persist(given).getOID();
        entityManager.flush();

        /* When */
        Optional<ContactDetailsImpl> found = contactDetailsRepository.findById(oID);

        /* Then */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

}
