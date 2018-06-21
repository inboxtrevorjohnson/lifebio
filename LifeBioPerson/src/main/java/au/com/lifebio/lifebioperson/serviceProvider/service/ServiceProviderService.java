package au.com.lifebio.lifebioperson.serviceProvider.service;

import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/21.
 */

public interface ServiceProviderService {

    Optional<ServiceProvider> addServiceProvider(ServiceProvider serviceProvider);

    Optional<ServiceProvider> changeServiceProvider(ServiceProvider serviceProvider);

    Optional<ServiceProvider> findServiceProvider(Long oID);

    Optional<ServiceProvider> findServiceProviderByPractiseNumber(String practiseNumber);

    Optional<Set<ServiceProvider>> findAll();

    Optional<Set<ServiceProvider>> findByServiceProviderNameLike(String serviceProviderName);

    void deleteServiceProvider(ServiceProvider serviceProvider);

}
