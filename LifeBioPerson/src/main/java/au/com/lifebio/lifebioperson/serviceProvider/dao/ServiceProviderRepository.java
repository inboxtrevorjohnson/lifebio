package au.com.lifebio.lifebioperson.serviceProvider.dao;

import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;
import au.com.lifebio.lifebioperson.serviceProvider.ServiceProviderImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/18.
 */

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProviderImpl, Long> {

    Optional<ServiceProvider> findByPractiseNumber(String practiseNumber);

    Set<ServiceProvider> findByServiceProviderNameLike(String serviceProviderNameLike);
}
