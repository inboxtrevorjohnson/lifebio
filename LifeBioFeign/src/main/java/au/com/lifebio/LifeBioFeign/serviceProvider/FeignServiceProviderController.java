package au.com.lifebio.LifeBioFeign.serviceProvider;

import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.OID_PATH_VARIABLE;
import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.PRACTISE_NUMBER;
import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.SERVICE_PROVIDER_NAME;

@RefreshScope
@RestController
public class FeignServiceProviderController {
        private final String SERVICE_PROVIDER_URL = "/dashboard/serviceprovider";

        @Autowired
        ServiceProviderProxy proxyService;

        @PostMapping(value = SERVICE_PROVIDER_URL,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Object> addServiceProvider(@RequestBody ServiceProvider serviceProvider) {
                return proxyService.addServiceProvider(serviceProvider);
        }

        @PutMapping(value = SERVICE_PROVIDER_URL + OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Object> changeServiceProvider(@PathVariable(value = "oID") Long oID,
                                                           @RequestBody ServiceProvider serviceProvider) {
               return proxyService.changeServiceProvider(oID, serviceProvider);
        }

        @GetMapping(value = SERVICE_PROVIDER_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ServiceProvider findServiceProvider(@PathVariable(value = "oID") Long oID) {
                return proxyService.findServiceProvider(oID);
        }

        @GetMapping(value = SERVICE_PROVIDER_URL + PRACTISE_NUMBER,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ServiceProvider findServiceProviderByPractiseNumber(
                @PathVariable(value = "practiseNumber") String practiseNumber) {
                return proxyService.findServiceProviderByPractiseNumber(practiseNumber);
        }

        @GetMapping(value = SERVICE_PROVIDER_URL + SERVICE_PROVIDER_NAME,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public Set<ServiceProvider> findServiceProviderByServiceProvider(
                @PathVariable(value = "serviceProviderName") String serviceProviderName) {
                return proxyService.findServiceProviderByServiceProviderNameLike(serviceProviderName);
        }

        @GetMapping(value = SERVICE_PROVIDER_URL + "/all", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public Set<ServiceProvider> findAllServiceProvider() {
                return proxyService.findAllServiceProvider();
        }

        @DeleteMapping(value = SERVICE_PROVIDER_URL + OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public void deleteServiceProvider(@PathVariable(value = "oID") Long oID) {
                proxyService.deleteServiceProvider(oID);
        }

}