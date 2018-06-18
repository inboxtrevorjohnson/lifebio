package au.com.lifebio.lifebioperson.serviceProvider;

import au.com.lifebio.lifebioperson.serviceProvider.dao.ServiceProviderRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Trevor on 2018/06/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ServiceProviderRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Test
    public void whenFindByPractiseNumber_thenReturnServiceProvider() {
        /* Given */
        String name = RandomStringUtils.random(10);
        String practiseNumber = RandomStringUtils.random(10);

        ServiceProvider given = new ServiceProviderImpl();
        given.setServiceProviderName(name);
        given.setPractiseNumber(practiseNumber);
        given.setLastModified(LocalDateTime.now());

        entityManager.persist(given);
        entityManager.flush();

        /* When */
        ServiceProvider found = serviceProviderRepository.findByPractiseNumber(practiseNumber);

        /* Then */
        assertThat(found.getPractiseNumber()).isEqualTo(practiseNumber);
    }

}
