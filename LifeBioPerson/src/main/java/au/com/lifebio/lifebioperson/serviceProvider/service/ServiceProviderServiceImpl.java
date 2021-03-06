package au.com.lifebio.lifebioperson.serviceProvider.service;

import au.com.lifebio.lifebiocommon.common.exception.ConflictException;
import au.com.lifebio.lifebiocommon.common.exception.TypeNotSupportedException;
import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;
import au.com.lifebio.lifebioperson.serviceProvider.ServiceProviderImpl;
import au.com.lifebio.lifebioperson.serviceProvider.dao.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class ServiceProviderServiceImpl implements ServiceProviderService {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Override
    @PreAuthorize("hasAuthority('SERVICE_PROVIDER_CREATE')")
    public Optional<ServiceProvider> addServiceProvider(@NotNull(message = "Cannot add null service provider.")
                                                                    ServiceProvider serviceProvider) {
        serviceProvider.setLastModified(LocalDateTime.now());
        return Optional.of(serviceProviderRepository.save((ServiceProviderImpl)serviceProvider));
    }

    @Override
    @PreAuthorize("hasAuthority('SERVICE_PROVIDER_UPDATE')")
    public Optional<ServiceProvider> changeServiceProvider(@NotNull(message = "Cannot change null service provider.")
                                                                       ServiceProvider serviceProvider) {
        if(!serviceProvider.getLastModified().equals(serviceProviderRepository.findById(serviceProvider.getOID())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot change service provider, cannot find service " +
                        "provider!") ).getLastModified())){
            throw new ConflictException("Cannot change service provider, it has been modified by someone else!");
        }
        if(serviceProvider instanceof ServiceProviderImpl) {
            serviceProvider.setLastModified(LocalDateTime.now());
            return Optional.of(serviceProviderRepository.save((ServiceProviderImpl)serviceProvider));
        }
        throw new TypeNotSupportedException("ServiceProvider is not a supported type!");
    }

    @Override
    @PreAuthorize("hasAuthority('SERVICE_PROVIDER_READ')")
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
    @PreAuthorize("hasAuthority('SERVICE_PROVIDER_READ')")
    public Optional<ServiceProvider> findServiceProvider(@NotNull(message = "Cannot find service provider with a " +
            "null OID.") Long oID) {
        Optional<ServiceProvider> optional = Optional.of(serviceProviderRepository.findById(oID).orElseThrow(
                () -> new ResourceNotFoundException("Unable to find service provider by OID")
        ));
        return optional;
    }

    @Override
    @PreAuthorize("hasAuthority('SERVICE_PROVIDER_READ')")
    public Optional<Set<ServiceProvider>> findAll() {
        return Optional.of(new HashSet<ServiceProvider>(serviceProviderRepository.findAll()));
    }

    @Override
    @PreAuthorize("hasAuthority('SERVICE_PROVIDER_READ')")
    public Optional<Set<ServiceProvider>> findByServiceProviderNameLike(@NotEmpty(message = "Cannot find service " +
            "provider with an empty surname.") String serviceProviderName) {
        return Optional.of(serviceProviderRepository.findByServiceProviderNameIgnoreCaseContaining(
                "%" + serviceProviderName + "%"));
    }

    @Override
    @PreAuthorize("hasAuthority('SERVICE_PROVIDER_DELETE')")
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
