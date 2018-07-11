package au.com.lifebio.lifebiocontactdetails.contact;

import au.com.lifebio.lifebiocommon.common.exception.CreationException;
import au.com.lifebio.lifebiocontactdetails.LifeBioContactDetailsApplication;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactDetailsRepository;
import au.com.lifebio.lifebiocontactdetails.contact.dao.ContactNumberRepository;
import au.com.lifebio.lifebiocontactdetails.contact.model.*;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactDetailsService;
import au.com.lifebio.lifebiocontactdetails.contact.service.ContactNumberService;
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

import static au.com.lifebio.lifebiocontactdetails.contact.controller.ContactDetailsContactNumberController.CONTACT_NUMBER_URL;
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
public class ContactNumberControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private ContactDetails contactDetails;

    private ContactNumber contactNumber;

    @Autowired
    private ContactDetailsService contactDetailsService;

    @Autowired
    private ContactNumberService contactNumberService;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    private ContactNumberRepository contactNumberRepository;

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

        this.contactNumberRepository.deleteAll();

        /* Setup*/
        contactDetails = new ContactDetailsImpl();
        contactNumber = new ContactNumberImpl();
        contactNumber.setNumber(RandomStringUtils.randomNumeric(10));
        contactNumber.setContactType(ContactType.BUSINESS);

        this.contactDetails = contactDetailsService.addContactDetails(contactDetails).orElseThrow(
                () -> new CreationException("Cannot set up test, unable to add contact details."));

        this.contactNumber = contactNumberService.addContactDetailsContactNumber(contactDetails.getOID(),
                contactNumber).orElseThrow(() -> new CreationException("Cannot set up test, unable to add contact " +
                "details."));
    }

    @Test
    public void readSingleContactDetailsContactNumber() throws Exception {
        mockMvc.perform(get(CONTACT_NUMBER_URL + "/" + contactNumber.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.contactNumber.getOID().intValue())))
                .andExpect(jsonPath("$.number", is(this.contactNumber.getNumber())));
    }

    @Test
    public void createContactDetailsContactNumber() throws Exception {
        /* Setup*/
        ContactNumber create = new ContactNumberImpl();
        create.setNumber(RandomStringUtils.randomNumeric(10));
        create.setContactType(ContactType.PERSONAL);

        String contactDetailsJson = json(create);

        this.mockMvc.perform(post(CONTACT_NUMBER_URL + "/" + contactDetails.getOID())
                .contentType(contentType)
                .content(contactDetailsJson))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void changeContactDetailsContactNumber() throws Exception {
        String contactNumberJson = json(contactNumber);

        this.mockMvc.perform(put(CONTACT_NUMBER_URL + "/" + contactNumber.getOID())
                .contentType(contentType)
                .content(contactNumberJson))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(get(CONTACT_NUMBER_URL+ "/" + contactNumber.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.oid", is(this.contactNumber.getOID().intValue())))
                .andExpect(jsonPath("$.lastModified", not(this.contactNumber.getLastModified())));
    }

    @Test
    public void listContactDetailsContactNumber() throws Exception {
        /* Setup*/
        long contactNumberOIDs[] = new long[10];

        for(int i = 0; i < 9; i++){
            ContactNumber create = new ContactNumberImpl();
            create.setNumber(RandomStringUtils.randomNumeric(10));
            create.setContactType(ContactType.PERSONAL);
            create = contactNumberService.addContactDetailsContactNumber(contactDetails.getOID(), create).orElseThrow(
                    () -> new CreationException("Cannot set up test for list, unable to add contact numbers."));
            contactNumberOIDs[i] = create.getOID();
        }

        mockMvc.perform(get(CONTACT_NUMBER_URL + "/all/" + contactDetails.getOID())
                .contentType(contentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    public void deleteContactDetailsContactNumber() throws Exception {

        String contactDetailsJson = json(contactNumber);

        this.mockMvc.perform(delete(CONTACT_NUMBER_URL + "/" + contactDetails.getOID() + "/" + contactNumber.getOID())
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