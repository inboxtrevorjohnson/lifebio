package au.com.lifebio.lifebioperson.serviceProvider.controller;

import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;
import org.springframework.http.ResponseEntity;

import java.util.Set;

/**
 * Created by Trevor on 2018/07/11.
 */
public interface ServiceProviderController {

    String SERVICE_PROVIDER_URL = "/serviceProvider";
    String PRACTISE_NUMBER = "/practiseNumber/{practiseNumber}";
    String SERVICE_PROVIDER_NAME = "/serviceProviderName/{serviceProviderName}";
    String OID_PATH_VARIABLE = "/{oID}";

    ResponseEntity<Object> addServiceProvider(ServiceProvider serviceProvider);

    ResponseEntity<Object>  changeServiceProvider(Long oID, ServiceProvider serviceProvider);

    ServiceProvider findServiceProviderByOID(Long oID);

    ServiceProvider findServiceProviderByPractiseNumber(String practiseNumber);

    Set<ServiceProvider> findServiceProviderByServiceProviderNameLike(String serviceProviderName);

    Set<ServiceProvider> findAllServiceProviders();

    void deleteServiceProvider(Long oID);



}
