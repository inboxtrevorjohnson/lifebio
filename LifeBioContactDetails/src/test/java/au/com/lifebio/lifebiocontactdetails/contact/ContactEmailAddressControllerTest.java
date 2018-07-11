package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebiocontactdetails.LifeBioContactDetailsApplication;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactEmailAddressRepository;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.*;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactEmailAddressService;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactDetailsService;
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

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsContactEmailAddressController
        .CONTACT_EMAIL_ADDRESS_URL;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Trevor on 2018/07/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LifeBioContactDetailsApplication.class)
@WebAppConfiguration
@Profile("test")
public class ContactEmailAddressControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private ContactDetails contactDetails;

    private ContactEmailAddress contactEmailAddress;

    @Autowired
    private ContactDetailsService contactDetailsService;

    @Autowired
    private ContactEmailAddressService contactEmailAddressService;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    private ContactEmailAddressRepository contactEmailAddressRepository;

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

        this.contactEmailAddressRepository.deleteAll();

        /* Setup*/
        contactDetails = new ContactDetailsImpl();
        contactEmailAddress = new ContactEmailAddressImpl();
        contactEmailAddress.setEmailAddress(RandomStringUtils.randomAlphabetic(5) + "@" +
                RandomStringUtils.randomAlphabetic(10) + "." + RandomStringUtils.randomAlphabetic(3));
        contactEmailAddress.setContactType(ContactType.BUSINESS);

        this.contactDetails = contactDetailsService.addContactDetails(contactDetails).orElseThrow(
                () -> new CreationException("Cannot set up test, unable to add contact details."));

        this.contactEmailAddress = contactEmailAddressService.addContactDetailsContactEmailAddress(contactDetails.getOID(),
                contactEmailAddress).orElseThrow(() -> new CreationException("Cannot set up test, unable to add contact " +
                "details."));
    }

    @Test
    public void readSingleContactDetailsContactEmailAddress() throws Exception {
        mockMvc.perform(get(CONTACT_EMAIL_ADDRESS_URL + "/" + contactEmailAddress.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.contactEmailAddress.getOID().intValue())))
                .andExpect(jsonPath("$.emailAddress", is(this.contactEmailAddress.getEmailAddress())));
    }

    @Test
    public void createContactDetailsContactEmailAddress() throws Exception {
        /* Setup*/
        ContactEmailAddress create = new ContactEmailAddressImpl();
        create.setEmailAddress(RandomStringUtils.randomAlphabetic(5) + "@" +
                RandomStringUtils.randomAlphabetic(10) + "." + RandomStringUtils.randomAlphabetic(3));
        create.setContactType(ContactType.PERSONAL);

        String contactDetailsJson = json(create);

        this.mockMvc.perform(post(CONTACT_EMAIL_ADDRESS_URL + "/" + contactDetails.getOID())
                .contentType(contentType)
                .content(contactDetailsJson))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void changeContactDetailsContactEmailAddress() throws Exception {
        String contactEmailAddressJson = json(contactEmailAddress);

        this.mockMvc.perform(put(CONTACT_EMAIL_ADDRESS_URL + "/" + contactEmailAddress.getOID())
                .contentType(contentType)
                .content(contactEmailAddressJson))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(get(CONTACT_EMAIL_ADDRESS_URL+ "/" + contactEmailAddress.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.contactEmailAddress.getOID().intValue())))
                .andExpect(jsonPath("$.lastModified", not(this.contactEmailAddress.getLastModified())));
    }

    @Test
    public void listContactDetailsContactEmailAddress() throws Exception {
        /* Setup*/
        long contactEmailAddressOIDs[] = new long[10];

        for(int i = 0; i < 9; i++){
            ContactEmailAddress create = new ContactEmailAddressImpl();
            create.setEmailAddress(RandomStringUtils.randomAlphabetic(5) + "@" +
                    RandomStringUtils.randomAlphabetic(10) + "." + RandomStringUtils.randomAlphabetic(3));
            create.setContactType(ContactType.PERSONAL);
            create = contactEmailAddressService.addContactDetailsContactEmailAddress(contactDetails.getOID(), create)
                    .orElseThrow(() -> new CreationException("Cannot set up test for list, unable to add contact email " +
                            "addresses."));
            contactEmailAddressOIDs[i] = create.getOID();
        }

        mockMvc.perform(get(CONTACT_EMAIL_ADDRESS_URL + "/all/" + contactDetails.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    public void deleteContactDetailsContactEmailAddress() throws Exception {

        String contactDetailsJson = json(contactEmailAddress);

        this.mockMvc.perform(delete(CONTACT_EMAIL_ADDRESS_URL + "/" + contactDetails.getOID() + "/" +
                contactEmailAddress.getOID())
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