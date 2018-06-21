package au.com.lifebio.lifebioperson.serviceProvider;

import au.com.lifebio.lifebioperson.serviceProvider.dao.ServiceProviderRepository;
import au.com.lifebio.lifebioperson.serviceProvider.service.ServiceProviderService;
import au.com.lifebio.lifebioperson.serviceProvider.service.ServiceProviderServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Trevor on 2018/06/18.
 */
@RunWith(SpringRunner.class)
public class ServiceProviderServiceTest {

    @Autowired
    private ServiceProviderService serviceProviderService;
    @MockBean
    private ServiceProviderRepository serviceProviderRepository;


    /* Given */
    private ServiceProvider given = new ServiceProviderImpl();
    private String practiseNumber = RandomStringUtils.random(10);
    private String serviceProviderName = RandomStringUtils.random(10);
    private LocalDateTime lastModified = LocalDateTime.now();
    private String like = RandomStringUtils.random(10);

    @Before
    public void setUp() {
        given.setPractiseNumber(practiseNumber);
        given.setServiceProviderName(serviceProviderName);
        given.setLastModified(lastModified);

        Optional<ServiceProvider> optional = Optional.of(given);
        Optional<ServiceProviderImpl> optionalImpl = Optional.of((ServiceProviderImpl) given);

        /* Add and Change */
        Mockito.when(serviceProviderRepository.save((ServiceProviderImpl)given)).thenReturn((ServiceProviderImpl) given);

        /* Find By ID */
        Mockito.when(serviceProviderRepository.findById(given.getOID())).thenReturn(optionalImpl);

        /* Find All */
        ServiceProviderImpl [] list = {new ServiceProviderImpl(), new ServiceProviderImpl(), new ServiceProviderImpl(),
                new ServiceProviderImpl(), new ServiceProviderImpl()};
        Mockito.when(serviceProviderRepository.findAll()).thenReturn(Arrays.asList(list));

        /* Find Like */
        Set<ServiceProvider> set = Arrays.stream(list).limit(3).collect(Collectors.toSet());
        set.forEach(serviceProvider -> serviceProvider.setServiceProviderName(like));
        Mockito.when(serviceProviderRepository.findByServiceProviderNameLike(like)).thenReturn(set);

        /* Find By Identification Number */
        Mockito.when(serviceProviderRepository.findByPractiseNumber(given.getPractiseNumber())).thenReturn(optional);

    }

    @Test
    public void whenAdd_thenServiceProviderShouldBeFound() {
        Optional<ServiceProvider> added = serviceProviderService.addServiceProvider(serviceProviderRepository.save(
                (ServiceProviderImpl) given));

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getPractiseNumber()).isEqualTo(practiseNumber);
    }

    @Test
    public void whenChange_thenChangedServiceProviderShouldBeFound() {
        Optional<ServiceProvider> added = serviceProviderService.addServiceProvider(serviceProviderRepository.save(
                (ServiceProviderImpl) given));

        /* Check */
        assertThat(added.isPresent());
        assertThat(added.get().getPractiseNumber()).isEqualTo(practiseNumber);

        added.get().setPractiseNumber(RandomStringUtils.random(10));
        added.get().setServiceProviderName(RandomStringUtils.random(10));

        Optional<ServiceProvider> changedOptional = serviceProviderService.changeServiceProvider(added.get());

        /* Check Was Changed */
        assertThat(changedOptional.isPresent());
        assertThat(changedOptional.get().getPractiseNumber()).isNotEqualTo(practiseNumber);
        assertThat(changedOptional.get().getServiceProviderName()).isNotEqualTo(serviceProviderName);
        assertThat(changedOptional.get().getLastModified()).isNotEqualTo(lastModified);
    }

    @Test
    public void whenValidOID_thenServiceProviderShouldBeFound() {
        Long oID = serviceProviderRepository.save((ServiceProviderImpl) given).getOID();
        Optional<ServiceProvider> found = serviceProviderService.findServiceProvider(oID);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getOID()).isEqualTo(oID);
    }

    @Test
    public void whenValidPractiseNumber_thenServiceProviderShouldBeFound() {
        Optional<ServiceProvider> found = serviceProviderService.findServiceProviderByPractiseNumber(practiseNumber);

        /* Check */
        assertThat(found.isPresent());
        assertThat(found.get().getPractiseNumber()).isEqualTo(practiseNumber);
    }

    @Test
    public void whenFindAll_thenAllShouldBeFound() {
        Optional<Set<ServiceProvider>> optional = serviceProviderService.findAll();

        /* Check */
        assertThat(optional.isPresent());
        assertThat(optional.get().size()).isEqualTo(5);
    }

    @Test
    public void whenFindByServiceProviderNameLike_thenOnlyLikeServiceProviderShouldBeFound() {
        Optional<Set<ServiceProvider>> optional = serviceProviderService.findByServiceProviderNameLike(like);

        /* Check */
        assertThat(optional.isPresent());
        assertThat(optional.get().size()).isEqualTo(3);
    }

    @Test
    public void whenServiceProviderDeleted_thenShouldNotBeFound() {
        Optional<ServiceProvider> found = Optional.of(serviceProviderRepository.save((ServiceProviderImpl) given));
        assertThat(found.isPresent());
        serviceProviderService.deleteServiceProvider(found.get());
        Mockito.verify(serviceProviderRepository).delete((ServiceProviderImpl) found.get());
    }

    @TestConfiguration
    static class ServiceProviderServiceImplTestContextConfiguration {

        @Bean
        public ServiceProviderService serviceProviderService() {
            return new ServiceProviderServiceImpl();
        }
    }

}
