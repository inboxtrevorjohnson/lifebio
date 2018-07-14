package au.com.lifebio.LifeBioFeign.serviceProvider;

import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.*;

/**
 * Created by Trevor on 2018/07/12.
 */
@FeignClient(name = "LifeBioPerson")
@RibbonClient(name = "LifeBioPerson")
public interface ServiceProviderProxy {

        @PostMapping(value = SERVICE_PROVIDER_URL,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object> addServiceProvider(@RequestBody ServiceProvider serviceProvider);

        @PutMapping(value = SERVICE_PROVIDER_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object>  changeServiceProvider(@PathVariable(value = "oID") Long oID,
                                                     @RequestBody ServiceProvider contactDetails);

        @GetMapping(value = SERVICE_PROVIDER_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ServiceProvider findServiceProvider(@PathVariable(value = "oID") Long oID);

        @GetMapping(value = SERVICE_PROVIDER_URL + PRACTISE_NUMBER,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ServiceProvider findServiceProviderByPractiseNumber(
                @PathVariable(value = "practiseNumber") String practiseNumber);

        @GetMapping(value = SERVICE_PROVIDER_URL + SERVICE_PROVIDER_NAME,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public Set<ServiceProvider> findServiceProviderByServiceProviderNameLike(
                @PathVariable(value = "serviceProviderName")  String serviceProviderName);

        @GetMapping(value = SERVICE_PROVIDER_URL + "/all",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        Set<ServiceProvider> findAllServiceProvider();

        @DeleteMapping(value = SERVICE_PROVIDER_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
        void deleteServiceProvider(@PathVariable(value = "oID") Long oID);

}


