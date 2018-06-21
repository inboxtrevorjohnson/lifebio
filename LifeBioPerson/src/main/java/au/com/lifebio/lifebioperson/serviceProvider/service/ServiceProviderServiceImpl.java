package au.com.lifebio.lifebioperson.serviceProvider.service;

import au.com.lifebio.lifebioperson.exception.TypeNotSupportedException;
import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;
import au.com.lifebio.lifebioperson.serviceProvider.ServiceProviderImpl;
import au.com.lifebio.lifebioperson.serviceProvider.dao.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Trevor on 2018/06/18.
 */

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Override
    public Optional<ServiceProvider> addServiceProvider(@NotNull(message = "Cannot add null service provider.")
                                                                    ServiceProvider serviceProvider) {
        serviceProvider.setLastModified(LocalDateTime.now());
        return Optional.of(serviceProviderRepository.save((ServiceProviderImpl)serviceProvider));
    }

    @Override
    public Optional<ServiceProvider> changeServiceProvider(@NotNull(message = "Cannot change null service provider.")
                                                                       ServiceProvider serviceProvider) {
        if(serviceProvider instanceof ServiceProviderImpl) {
            serviceProvider.setLastModified(LocalDateTime.now());
            return Optional.of(serviceProviderRepository.save((ServiceProviderImpl)serviceProvider));
        }
        throw new TypeNotSupportedException("ServiceProvider is not a supported type!");
    }

    @Override
    public Optional<ServiceProvider> findServiceProviderByPractiseNumber(
            @NotEmpty(message = "Cannot find serviceProvider with an empty practise number.") String practiseNumber) {
        Optional<ServiceProvider> optional = Optional.of(serviceProviderRepository.findByPractiseNumber(
                practiseNumber)
                .orElseThrow(
                () -> new ResourceNotFoundException("Unable to find service provider by practise number")
        ));
        return optional;
    }

    @Override

    public Optional<ServiceProvider> findServiceProvider(@NotNull(message = "Cannot find service provider with a " +
            "null OID.") Long oID) {
        Optional<ServiceProvider> optional = Optional.of(serviceProviderRepository.findById(oID).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find service provider by OID")
        ));
        return optional;
    }

    @Override
    public Optional<Set<ServiceProvider>> findAll() {
        return Optional.of(new HashSet<ServiceProvider>(serviceProviderRepository.findAll()));
    }

    @Override
    public Optional<Set<ServiceProvider>> findByServiceProviderNameLike(@NotEmpty(message = "Cannot find service " +
            "provider with an empty surname.") String serviceProviderNameLike) {
        return Optional.of(serviceProviderRepository.findByServiceProviderNameLike(serviceProviderNameLike));
    }

    @Override
    public void deleteServiceProvider(@NotNull(message = "Cannot delete serviceProvider, a valid service provider must " +
            "be specified.") ServiceProvider serviceProvider) {
        if(serviceProvider instanceof ServiceProviderImpl) {
            serviceProviderRepository.delete((ServiceProviderImpl) serviceProvider);
        }
        else {
            throw new TypeNotSupportedException("ServiceProvider is not a supported type!");
        }

    }

}
