package au.com.lifebio.LifeBioFeign.serviceProvider;

import au.com.lifebio.LifeBioFeign.contact.ContactDetailsProxy;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetails;
import au.com.lifebio.lifebiocontactdetails.contact.model.ContactDetailsImpl;
import au.com.lifebio.lifebioperson.serviceProvider.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsController.CONTACT_DETAILS_OID;
import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsController.CONTACT_DETAILS_URL;
import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactMeansController.CONTACT_DETAILS_OID_PATH_VARIABLE;
import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.OID_PATH_VARIABLE;
import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.PRACTISE_NUMBER;
import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.SERVICE_PROVIDER_NAME;

@RefreshScope
@RestController
public class FeignServiceProviderController {
        private final String SERVICE_PROVIDER_URL = "/dashboard/serviceprovider";

        @Autowired
        ServiceProviderProxy serviceProviderProxyService;

        @Autowired
        ContactDetailsProxy contactDetailsProxyService;

        @PostMapping(value = SERVICE_PROVIDER_URL,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Object> addServiceProvider(@RequestBody ServiceProvider serviceProvider) {
                /* Add contact details for new service provider */
                ContactDetails contactDetails = new ContactDetailsImpl();
                ResponseEntity<Object> contactDetailsResponseEntity = contactDetailsProxyService
                        .addContactDetails(contactDetails);

                /* Set contact details on new service provider */
                serviceProvider.setContactDetails(Long.parseLong(contactDetailsResponseEntity.getHeaders().getFirst(
                        CONTACT_DETAILS_OID)));

                return serviceProviderProxyService.addServiceProvider(serviceProvider);
        }

        @PutMapping(value = SERVICE_PROVIDER_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Object> changeServiceProvider(@PathVariable(value = "oID") Long oID,
                                                           @RequestBody ServiceProvider serviceProvider) {
               return serviceProviderProxyService.changeServiceProvider(oID, serviceProvider);
        }

        @GetMapping(value = SERVICE_PROVIDER_URL + OID_PATH_VARIABLE,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ServiceProvider findServiceProvider(@PathVariable(value = "oID") Long oID) {
                return serviceProviderProxyService.findServiceProvider(oID);
        }

        @GetMapping(value = SERVICE_PROVIDER_URL + PRACTISE_NUMBER,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public ServiceProvider findServiceProviderByPractiseNumber(
                @PathVariable(value = "practiseNumber") String practiseNumber) {
                return serviceProviderProxyService.findServiceProviderByPractiseNumber(practiseNumber);
        }

        @GetMapping(value = SERVICE_PROVIDER_URL + SERVICE_PROVIDER_NAME,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public Set<ServiceProvider> findServiceProviderByServiceProvider(
                @PathVariable(value = "serviceProviderName") String serviceProviderName) {
                return serviceProviderProxyService.findServiceProviderByServiceProviderNameLike(serviceProviderName);
        }

        @GetMapping(value = SERVICE_PROVIDER_URL + "/all", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        public Set<ServiceProvider> findAllServiceProvider() {
                return serviceProviderProxyService.findAllServiceProvider();
        }

        @DeleteMapping(value = SERVICE_PROVIDER_URL + OID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public void deleteServiceProvider(@PathVariable(value = "oID") Long oID) {
                serviceProviderProxyService.deleteServiceProvider(oID);
        }

        /* Contact Details */
        @PostMapping(value = SERVICE_PROVIDER_URL + OID_PATH_VARIABLE + CONTACT_DETAILS_URL,
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Object> addServiceProviderContactDetails(
                @PathVariable(value = "oID") Long oID,
                @RequestBody ContactDetails contactDetails){
                /* Add contact details */
                ResponseEntity<Object> contactDetailsResponseEntity
                        = contactDetailsProxyService.addContactDetails(contactDetails);

                /* Set service provider contact details */
                ServiceProvider serviceProvider = serviceProviderProxyService.findServiceProvider(oID);
                serviceProvider.setContactDetails(Long.parseLong(contactDetailsResponseEntity.getHeaders().getFirst(
                        CONTACT_DETAILS_OID)));

                return serviceProviderProxyService.changeServiceProvider(oID, serviceProvider);
        }

}