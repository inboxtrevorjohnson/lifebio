package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebiocontactdetails.LifeBioContactDetailsApplication;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactAddressRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.*;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactDetailsService;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactAddressService;
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

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsContactAddressController
        .CONTACT_ADDRESS_URL;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Trevor on 2018/07/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LifeBioContactDetailsApplication.class)
@WebAppConfiguration
@Profile("test")
public class ContactAddressControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private ContactDetails contactDetails;

    private ContactAddress contactAddress;

    @Autowired
    private ContactDetailsService contactDetailsService;

    @Autowired
    private ContactAddressService contactAddressService;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    private ContactAddressRepository contactAddressRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",  this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.contactDetailsRepository.deleteAll();

        this.contactAddressRepository.deleteAll();

        /* Setup*/
        contactDetails = new ContactDetailsImpl();
        contactAddress = new ContactAddressImpl();
        contactAddress.setLine1(RandomStringUtils.randomAlphabetic(10));
        contactAddress.setLine2(RandomStringUtils.randomAlphabetic(10));
        contactAddress.setLine3(RandomStringUtils.randomAlphabetic(10));
        contactAddress.setCityArea(RandomStringUtils.randomAlphabetic(10));
        contactAddress.setState(RandomStringUtils.randomAlphabetic(10));
        contactAddress.setCountry(RandomStringUtils.randomAlphabetic(10));
        contactAddress.setPostalCode(RandomStringUtils.randomAlphabetic(10));
        contactAddress.setContactType(ContactType.BUSINESS);

        this.contactDetails = contactDetailsService.addContactDetails(contactDetails).orElseThrow(
                () -> new CreationException("Cannot set up test, unable to add contact details."));

        this.contactAddress = contactAddressService.addContactDetailsContactAddress(contactDetails.getOID(),
                contactAddress).orElseThrow(() -> new CreationException("Cannot set up test, unable to add contact " +
                "details."));
    }

    @Test
    public void readSingleContactDetailsContactAddress() throws Exception {
        mockMvc.perform(get(CONTACT_ADDRESS_URL + "/" + contactAddress.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.contactAddress.getOID().intValue())))
                .andExpect(jsonPath("$.line1", is(this.contactAddress.getLine1())))
                .andExpect(jsonPath("$.line2", is(this.contactAddress.getLine2())))
                .andExpect(jsonPath("$.line3", is(this.contactAddress.getLine3())))
                .andExpect(jsonPath("$.cityArea", is(this.contactAddress.getCityArea())))
                .andExpect(jsonPath("$.state", is(this.contactAddress.getState())))
                .andExpect(jsonPath("$.country", is(this.contactAddress.getCountry())))
                .andExpect(jsonPath("$.postalCode", is(this.contactAddress.getPostalCode())));
    }

    @Test
    public void createContactDetailsContactAddress() throws Exception {
        /* Setup*/
        ContactAddress create = new ContactAddressImpl();
        create.setLine1(RandomStringUtils.randomAlphabetic(10));
        create.setLine2(RandomStringUtils.randomAlphabetic(10));
        create.setLine3(RandomStringUtils.randomAlphabetic(10));
        create.setCityArea(RandomStringUtils.randomAlphabetic(10));
        create.setState(RandomStringUtils.randomAlphabetic(10));
        create.setCountry(RandomStringUtils.randomAlphabetic(10));
        create.setPostalCode(RandomStringUtils.randomAlphabetic(10));
        create.setContactType(ContactType.PERSONAL);

        String contactDetailsJson = json(create);

        this.mockMvc.perform(post(CONTACT_ADDRESS_URL + "/" + contactDetails.getOID())
                .contentType(contentType)
                .content(contactDetailsJson))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void changeContactDetailsContactAddress() throws Exception {
        String contactAddressJson = json(contactAddress);

        this.mockMvc.perform(put(CONTACT_ADDRESS_URL + "/" + contactAddress.getOID())
                .contentType(contentType)
                .content(contactAddressJson))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(get(CONTACT_ADDRESS_URL+ "/" + contactAddress.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.contactAddress.getOID().intValue())))
                .andExpect(jsonPath("$.lastModified", not(this.contactAddress.getLastModified())));
    }

    @Test
    public void listContactDetailsContactAddress() throws Exception {
        /* Setup*/
        long contactAddressOIDs[] = new long[10];

        for(int i = 0; i < 9; i++){
            ContactAddress create = new ContactAddressImpl();
            create.setLine1(RandomStringUtils.randomAlphabetic(10));
            create.setLine2(RandomStringUtils.randomAlphabetic(10));
            create.setLine3(RandomStringUtils.randomAlphabetic(10));
            create.setCityArea(RandomStringUtils.randomAlphabetic(10));
            create.setState(RandomStringUtils.randomAlphabetic(10));
            create.setCountry(RandomStringUtils.randomAlphabetic(10));
            create.setPostalCode(RandomStringUtils.randomAlphabetic(10));
            create.setContactType(ContactType.PERSONAL);
            create = contactAddressService.addContactDetailsContactAddress(contactDetails.getOID(), create).orElseThrow(
                    () -> new CreationException("Cannot set up test for list, unable to add contact addresses."));
            contactAddressOIDs[i] = create.getOID();
        }

        mockMvc.perform(get(CONTACT_ADDRESS_URL + "/all/" + contactDetails.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    public void deleteContactDetailsContactAddress() throws Exception {

        String contactDetailsJson = json(contactAddress);

        this.mockMvc.perform(delete(CONTACT_ADDRESS_URL + "/" + contactDetails.getOID() + "/" + contactAddress.getOID())
                .contentType(contentType))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    protected String json(Object object) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(object, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}