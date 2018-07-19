package au.com.lifebio.lifebioperson.serviceProvider;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebioperson.LifeBioPersonApplication;
import au.com.lifebio.lifebioperson.serviceProvider.dao.ServiceProviderRepository;
import au.com.lifebio.lifebioperson.serviceProvider.service.ServiceProviderService;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.PRACTISE_NUMBER;
import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.SERVICE_PROVIDER_NAME;
import static au.com.lifebio.lifebioperson.serviceProvider.controller.ServiceProviderController.SERVICE_PROVIDER_URL;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Trevor on 2018/07/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LifeBioPersonApplication.class)
@WebAppConfiguration
@Profile("test")
public class ServiceProviderControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private ServiceProvider serviceProvider;

    @Autowired
    private ServiceProviderService serviceProviderService;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.serviceProviderRepository.deleteAll();

        /* Setup*/
        serviceProvider = new ServiceProviderImpl();
        serviceProvider.setPractiseNumber(RandomStringUtils.randomAlphanumeric(10));
        serviceProvider.setServiceProviderName(RandomStringUtils.randomAlphabetic(10));

        this.serviceProvider = serviceProviderService.addServiceProvider(serviceProvider).orElseThrow(
                () -> new CreationException("Cannot set up test, unable to add service provider."));
    }

    @Test
    public void readSingleServiceProviderByOID() throws Exception {
        mockMvc.perform(get(SERVICE_PROVIDER_URL + "/" + serviceProvider.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.serviceProvider.getOID().intValue())))
                .andExpect(jsonPath("$.practiseNumber", is(this.serviceProvider.getPractiseNumber())))
                .andExpect(jsonPath("$.serviceProviderName", is(this.serviceProvider.getServiceProviderName())));
    }

    @Test
    public void readSingleServiceProviderByPractiseNumber() throws Exception {
        mockMvc.perform(get(SERVICE_PROVIDER_URL + "/practisenumber/" + serviceProvider.getPractiseNumber())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.serviceProvider.getOID().intValue())))
                .andExpect(jsonPath("$.practiseNumber", is(this.serviceProvider.getPractiseNumber())))
                .andExpect(jsonPath("$.serviceProviderName", is(this.serviceProvider.getServiceProviderName())));
    }

    @Test
    public void readServiceProvidersByServiceProviderNameContaining() throws Exception {
        System.out.println("URL ============ " + SERVICE_PROVIDER_URL + "/serviceprovidername/" + serviceProvider
                .getServiceProviderName());
        mockMvc.perform(get(SERVICE_PROVIDER_URL + "/serviceprovidername/" + serviceProvider
                .getServiceProviderName())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createServiceProvider() throws Exception {
        /* Setup*/
        ServiceProvider create = new ServiceProviderImpl();
        create.setPractiseNumber(RandomStringUtils.randomAlphabetic(10));
        create.setServiceProviderName(RandomStringUtils.randomAlphabetic(10));

        String serviceProviderJson = json(create);

        this.mockMvc.perform(post(SERVICE_PROVIDER_URL)
                .contentType(contentType)
                .content(serviceProviderJson))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void changeServiceProvider() throws Exception {
        String serviceProviderJson = json(serviceProvider);

        this.mockMvc.perform(put(SERVICE_PROVIDER_URL + "/" + serviceProvider.getOID())
                .contentType(contentType)
                .content(serviceProviderJson))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(get(SERVICE_PROVIDER_URL + "/" + serviceProvider.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.serviceProvider.getOID().intValue())))
                .andExpect(jsonPath("$.lastModified", not(this.serviceProvider.getLastModified())));
    }

    @Test
    public void listServiceProvider() throws Exception {
        /* Setup*/
        long serviceProviderOIDs[] = new long[10];

        for(int i = 0; i < 9; i++){
            ServiceProvider create = new ServiceProviderImpl();
            create.setPractiseNumber(RandomStringUtils.randomAlphanumeric(10));
            create.setServiceProviderName(RandomStringUtils.randomAlphabetic(10));

            create = serviceProviderService.addServiceProvider(create).orElseThrow(
                    () -> new CreationException("Cannot set up test for list, unable to add service provider."));
            serviceProviderOIDs[i] = create.getOID();
        }

        String serviceProviderJson = json(serviceProviderOIDs);

        mockMvc.perform(get(SERVICE_PROVIDER_URL + "/all")
                .contentType(contentType)
                .content(serviceProviderJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    public void deleteServiceProvider() throws Exception {

        String serviceProviderJson = json(serviceProvider);

        this.mockMvc.perform(delete(SERVICE_PROVIDER_URL + "/" + serviceProvider.getOID())
                .contentType(contentType)
                .content(serviceProviderJson))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    protected String json(Object object) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(object, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}