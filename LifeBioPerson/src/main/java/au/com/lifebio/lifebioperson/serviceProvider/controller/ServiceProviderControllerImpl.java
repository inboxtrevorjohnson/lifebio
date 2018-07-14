package au.com.lifebio.lifebioperson.serviceProvider.controller;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebiocommon.common.exception.ResourceNotFoundException;
import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;
import au.com.lifebio.lifebioperson.serviceProvider.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.SERVICE_PROVIDER_URL;

/**
 * Created by Trevor on 2018/07/05.
 */
@RestController
@RequestMapping(value = SERVICE_PROVIDER_URL)
public class ServiceProviderControllerImpl implements ServiceProviderController {

    @Autowired
    ServiceProviderService serviceProviderService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addServiceProvider(@RequestBody ServiceProvider serviceProvider) {
        if(serviceProvider == null){
            throw new IllegalArgumentException("Cannot add service provider, a valid service provider must be " +
                    "specified!");
        }
        ServiceProvider addedServiceProvider = serviceProviderService.addServiceProvider(serviceProvider).orElseThrow(
                () -> new CreationException("Unable to add service provider!"));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(OID_PATH_VARIABLE) .buildAndExpand(
                addedServiceProvider.getOID()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @PutMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> changeServiceProvider(@PathVariable Long oID,
                                                       @RequestBody ServiceProvider serviceProvider) {
        if(oID == null || serviceProvider == null){
            throw new IllegalArgumentException("Cannot change service provider, a valid oid and service provider must " +
                    "be specified!");
        }
        Optional<ServiceProvider> serviceProviderOptional = serviceProviderService.findServiceProvider(oID);

        serviceProviderOptional.orElseThrow(() -> new ResourceNotFoundException("Cannot change service provider," +
                "cannot find service provider!"));

        serviceProvider.setOID(oID);

        serviceProviderService.changeServiceProvider(serviceProvider);

        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceProvider findServiceProviderByOID(@PathVariable Long oID) {
        if(oID == null || oID < 0){
            throw new IllegalArgumentException("Cannot find service provider, a valid oid must be specified!");
        }
        Optional<ServiceProvider> serviceProvider = serviceProviderService.findServiceProvider(oID);

        return serviceProvider.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find the service provider with the specified oid!"));
    }

    @Override
    @GetMapping(value = PRACTISE_NUMBER, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceProvider findServiceProviderByPractiseNumber(@PathVariable String practiseNumber) {
        if(practiseNumber == null || practiseNumber.length() < 1){
            throw new IllegalArgumentException("Cannot find service provider, a valid practise number must be " +
                    "specified!");
        }
        Optional<ServiceProvider> serviceProvider = serviceProviderService.findServiceProviderByPractiseNumber(
                practiseNumber);

        return serviceProvider.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find the service provider with the specified practise number!"));
    }

    @Override
    @GetMapping(value = SERVICE_PROVIDER_NAME, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ServiceProvider> findServiceProviderByServiceProviderNameLike(@PathVariable  String serviceProviderName) {
        if(serviceProviderName == null || serviceProviderName.length() < 3){
            throw new IllegalArgumentException("Cannot find service provider by service provider name, a valid name " +
                    "of at least three characters must be specified!");
        }
        Optional<Set<ServiceProvider>> serviceProviders = serviceProviderService.findByServiceProviderNameLike(
                serviceProviderName);
        return serviceProviders.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find the service provider matching the list of service provider name provided!"));
    }

    @Override
    @GetMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ServiceProvider> findAllServiceProviders() {
        Optional<Set<ServiceProvider>> serviceProviders = serviceProviderService.findAll();

        return serviceProviders.orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find any service providers!"));
    }

    @Override
    @DeleteMapping(value = OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteServiceProvider(@PathVariable Long oID) {
        if(oID == null){
            throw new IllegalArgumentException("Cannot delete service provider, a valid oid must be specified!");
        }
        serviceProviderService.deleteServiceProvider(serviceProviderService.findServiceProvider(oID).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Cannot delete the service provider with the oid of specified!")));
    }


}
