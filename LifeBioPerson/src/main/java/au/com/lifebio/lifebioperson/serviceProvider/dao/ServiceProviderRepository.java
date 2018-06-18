package au.com.lifebio.lifebioperson.serviceProvider.dao;

import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;
import au.com.lifebio.lifebioperson.serviceProvider.ServiceProviderImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Trevor on 2018/06/18.
 */

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProviderImpl, Long> {

    ServiceProvider findByPractiseNumber(String practiseNumber);
}
