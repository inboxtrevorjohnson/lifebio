package au.com.lifebio.lifebiocontactdetails.contact;

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsController.CONTACT_DETAILS_URL;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebiocontactdetails.LifeBioContactDetailsApplication;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.*;
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

/**
 * Created by Trevor on 2018/07/06.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LifeBioContactDetailsApplication.class)
@WebAppConfiguration
@Profile("test")
public class ContactDetailsControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private ContactDetails contactDetails;

    @Autowired
    private ContactDetailsService contactDetailsService;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

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

        this.contactDetailsRepository.deleteAll();

        /* Setup*/
        contactDetails = new ContactDetailsImpl();

        ContactNumber contactNumber = new ContactNumberImpl();
        contactNumber.setNumber( RandomStringUtils.randomNumeric(10));
        contactNumber.setContactType(ContactType.BUSINESS);

        ContactEmailAddress contactEmailAddress = new ContactEmailAddressImpl();
        contactEmailAddress.setEmailAddress( RandomStringUtils.randomAlphabetic(5) + "@" + RandomStringUtils
                .randomAlphabetic(5) + "." + RandomStringUtils.randomAlphabetic(3));
        contactEmailAddress.setContactType(ContactType.BUSINESS);

        ContactAddress contactAddress = new ContactAddressImpl();
        contactAddress.setLine1( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setLine2( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setLine3( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setCityArea( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setState( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setPostalCode( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setCountry( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setContactType(ContactType.BUSINESS);

        contactDetails.getContactNumbers().add((ContactNumberImpl) contactNumber);
        contactDetails.getContactEmailAddresses().add((ContactEmailAddressImpl) contactEmailAddress);
        contactDetails.getContactAddresses().add((ContactAddressImpl) contactAddress);

        this.contactDetails = contactDetailsService.addContactDetails(contactDetails).orElseThrow(
                () -> new CreationException("Cannot set up test, unable to add contact details."));
    }

    @Test
    public void readSingleContactDetails() throws Exception {
        mockMvc.perform(get(CONTACT_DETAILS_URL + "/" + contactDetails.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.contactDetails.getOID().intValue())))
                .andExpect(jsonPath("$.contactNumbers", hasSize(1)))
                .andExpect(jsonPath("$.contactEmailAddresses", hasSize(1)))
                .andExpect(jsonPath("$.contactAddresses", hasSize(1)));
    }

    @Test
    public void createContactDetails() throws Exception {
        /* Setup*/
        ContactDetails create = new ContactDetailsImpl();

        ContactNumber contactNumber = new ContactNumberImpl();
        contactNumber.setNumber( RandomStringUtils.randomNumeric(10));
        contactNumber.setContactType(ContactType.BUSINESS);

        ContactEmailAddress contactEmailAddress = new ContactEmailAddressImpl();
        contactEmailAddress.setEmailAddress( RandomStringUtils.randomAlphabetic(5) + "@" + RandomStringUtils
                .randomAlphabetic(5) + "." + RandomStringUtils.randomAlphabetic(3));
        contactEmailAddress.setContactType(ContactType.BUSINESS);

        ContactAddress contactAddress = new ContactAddressImpl();
        contactAddress.setLine1( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setLine2( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setLine3( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setCityArea( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setState( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setPostalCode( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setCountry( RandomStringUtils.randomAlphanumeric(10));
        contactAddress.setContactType(ContactType.BUSINESS);

        create.getContactNumbers().add((ContactNumberImpl) contactNumber);
        create.getContactEmailAddresses().add((ContactEmailAddressImpl) contactEmailAddress);
        create.getContactAddresses().add((ContactAddressImpl) contactAddress);

        String contactDetailsJson = json(create);

        this.mockMvc.perform(post(CONTACT_DETAILS_URL)
                .contentType(contentType)
                .content(contactDetailsJson))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void changeContactDetails() throws Exception {
        String contactDetailsJson = json(contactDetails);

        this.mockMvc.perform(put(CONTACT_DETAILS_URL + "/" + contactDetails.getOID())
                .contentType(contentType)
                .content(contactDetailsJson))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(get(CONTACT_DETAILS_URL + "/" + contactDetails.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.contactDetails.getOID().intValue())))
                .andExpect(jsonPath("$.lastModified", not(this.contactDetails.getLastModified())));
    }

    @Test
    public void listContactDetails() throws Exception {
        /* Setup*/
        long contactDetailOIDs[] = new long[10];

        for(int i = 0; i < 10; i++){
            ContactDetails create = new ContactDetailsImpl();
            create = contactDetailsService.addContactDetails(create).orElseThrow(
                    () -> new CreationException("Cannot set up test for list, unable to add contact details."));
            contactDetailOIDs[i] = create.getOID();
        }

        String contactDetailsJson = json(contactDetailOIDs);

        mockMvc.perform(get(CONTACT_DETAILS_URL + "/all")
                .contentType(contentType)
                .content(contactDetailsJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    public void deleteContactDetails() throws Exception {

        String contactDetailsJson = json(contactDetails);

        this.mockMvc.perform(delete(CONTACT_DETAILS_URL + "/" + contactDetails.getOID())
                .contentType(contentType)
                .content(contactDetailsJson))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    protected String json(Object object) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(object, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}