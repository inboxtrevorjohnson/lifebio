package au.com.lifebio.lifebioperson.person.patient.controller;

import au.com.lifebio.lifebioperson.common.controller.CommonController;
import au.com.lifebio.lifebioperson.exception.ResourceNotFoundException;
import au.com.lifebio.lifebioperson.person.patient.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>PatientController</code> is used as the 'controller' to
 * produce and consume all patient related resources.
 *
 * @author <a href="mailto:inboxtrevorjohnson@gmail.com">ALR</a>
 */

@RestController
@RequestMapping("/patient")
public class PatientController extends CommonController {

    @RequestMapping(method= RequestMethod.GET, produces="application/json")
    public List<Patient> listPatients(){
        return new ArrayList<>();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(value="/{oID}", method=RequestMethod.GET)
    public Patient getPatient(@PathVariable Long oID) {
        Patient patient = null;
        if (patient == null) {
            throw new ResourceNotFoundException("No patient with the specified id could be found!");
        }
        return null;
    }



}
